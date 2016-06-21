package app;

import app.handlers.TodosHandler;
import ratpack.handling.Context;
import ratpack.server.PublicAddress;
import ratpack.server.RatpackServer;

public class Main {
    public static void main(String... args) throws Exception {

        RatpackServer.start(server -> server
            .handlers(chain -> chain
                .all(ctx -> {
                    ctx.getResponse().getHeaders()
                        .add("access-control-allow-origin", "*")
                        .add("access-control-allow-methods", "GET,PUT,PATCH,POST,DELETE,OPTIONS")
                        .add("access-control-allow-headers", "Content-Type,X-Requested-With");

                    ctx.next();
                })
                .prefix("todos", new TodosHandler())
            )
        );
    }

    public static String getUrl(Context ctx) {
        return ctx.get(PublicAddress.class).get() + "/todos";
    }
}
