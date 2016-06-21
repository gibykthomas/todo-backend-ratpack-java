package app.handlers;

import app.models.CreateTodo;
import app.models.UpdateTodo;
import app.services.TodoService;
import ratpack.exec.Blocking;
import ratpack.func.Action;
import ratpack.handling.Chain;

import java.util.Collections;

import static app.Main.getUrl;
import static java.util.stream.Collectors.toList;
import static ratpack.jackson.Jackson.json;

public class TodosHandler implements Action<Chain> {

    @Override
    public void execute(Chain chain) throws Exception {

        TodoService service = new TodoService();

        chain
            .path(":id", ctx -> {
                int id = ctx.getPathTokens().asInt("id");
                ctx.byMethod(m -> m
                    .get(() -> {
                        Blocking
                            .get(() -> service.find(id))
                            .then(todo -> {
                                if (todo != null) {
                                    ctx.render(json(todo.withHref(getUrl(ctx))));
                                } else {
                                    ctx.clientError(404);
                                }
                            });
                    })
                    .delete(() -> {
                        Blocking
                            .op(() -> service.delete(id))
                            .then(() -> ctx.getResponse().status(200).send());
                    })
                    .patch(() -> {
                        ctx.parse(UpdateTodo.class)
                            .then(updateTodo -> {
                                Blocking
                                    .op(() -> service.update(id, updateTodo))
                                    .map(() -> service.find(id))
                                    .then(todo -> ctx.render(json(todo.withHref(getUrl(ctx)))));
                            });
                    })
                );
            })
            .all(ctx -> ctx
                .byMethod(m -> m
                    .get(() -> {
                        Blocking
                            .get(() -> service.findAll())
                            .then(todos -> {
                                String url = getUrl(ctx);
                                ctx.render(json(
                                    todos
                                        .stream()
                                        .map(t -> t.withHref(url))
                                        .collect(toList())
                                ));
                            });
                    })
                    .post(() -> {
                        ctx.parse(CreateTodo.class)
                            .then(newTodo -> {
                                Blocking
                                    .get(() -> service.add(newTodo))
                                    .then(id -> {
                                        Blocking
                                            .get(() -> service.find(id))
                                            .then(todo -> ctx.render(json(todo.withHref(getUrl(ctx)))));
                                    });
                            });
                    })
                    .delete(() -> {
                        Blocking
                            .op(() -> service.deleteAll())
                            .then(() -> ctx.render(json(Collections.emptyList())));
                    })
                )
            );
    }
}
