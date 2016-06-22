package app.handlers;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class CorsHandler implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        ctx.getResponse()
            .getHeaders()
            .add("access-control-allow-origin", "*")
            .add("access-control-allow-methods", "GET,PUT,PATCH,POST,DELETE,OPTIONS")
            .add("access-control-allow-headers", "Content-Type,X-Requested-With");

        ctx.next();
    }
}
