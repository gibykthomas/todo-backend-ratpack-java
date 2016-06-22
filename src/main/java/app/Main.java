package app;

import app.handlers.CorsHandler;
import app.handlers.TodosHandler;
import ratpack.handling.Context;
import ratpack.server.PublicAddress;
import ratpack.server.RatpackServer;

public class Main {
    public static void main(String... args) throws Exception {

        RatpackServer.start(server -> server
            .handlers(chain -> chain
                .all(new CorsHandler())
                .prefix("todos", new TodosHandler())
            )
        );
    }

    public static String getUrl(Context ctx) {
        return ctx.get(PublicAddress.class).get() + "/todos";
    }
}
