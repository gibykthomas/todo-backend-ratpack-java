package app;

import app.handlers.CorsHandler;
import app.handlers.TodosHandler;
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
}
