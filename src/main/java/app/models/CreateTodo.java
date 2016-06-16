package app.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateTodo {

    public final String title;
    public final int order;

    public CreateTodo(
        @JsonProperty("title") String title,
        @JsonProperty("order") int order
    ) {
        this.title = title;
        this.order = order;
    }
}
