package app;

import app.models.CreateTodo;
import app.services.TodoService;
import ratpack.exec.Blocking;
import ratpack.server.RatpackServer;

import java.util.Collections;

import static ratpack.jackson.Jackson.json;

public class Main {
    public static void main(String... args) throws Exception {
        TodoService service = new TodoService();

        RatpackServer.start(server -> server
            .handlers(chain -> chain
                .all(ctx -> {
                    ctx.getResponse().getHeaders()
                        .add("access-control-allow-origin", "*")
                        .add("access-control-allow-methods", "GET,PUT,POST,DELETE,OPTIONS")
                        .add("access-control-allow-headers", "Content-Type,X-Requested-With");

                    ctx.next();
                })
                .prefix("todos", c -> c
                    .path(":id", ctx -> {
                        int id = ctx.getPathTokens().asInt("id");
                        ctx.byMethod(m -> m
                            .get(() -> {
                                Blocking
                                    .get(() -> service.find(id))
                                    .then(todo -> ctx.render(json(todo)));
                            })
                            .delete(() -> {
                                Blocking
                                    .op(() -> service.delete(id))
                                    .then(() -> ctx.getResponse().status(200).send());
                            })
                        );
                    })
                    .all(ctx -> ctx
                        .byMethod(m -> m
                            .get(() -> {
                                Blocking
                                    .get(() -> service.findAll())
                                    .then(todos -> ctx.render(json(todos)));
                            })
                            .post(() -> {
                                ctx.parse(CreateTodo.class)
                                    .then(newTodo -> {
                                        Blocking
                                            .get(() -> service.add(newTodo))
                                            .then(id -> {
                                                Blocking
                                                    .get(() -> service.find(id))
                                                    .then(todo -> ctx.render(json(todo)));
                                            });
                                    });
                            })
                            .delete(() -> {
                                Blocking
                                    .op(() -> service.deleteAll())
                                    .then(() -> ctx.render(json(Collections.emptyList())));
                            })
                        )
                    )
                )
            )
        );
    }
}
