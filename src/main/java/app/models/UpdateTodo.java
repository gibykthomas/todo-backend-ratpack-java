package app.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateTodo {

    public final String title;
    public final boolean completed;
    public final int order;

    public UpdateTodo(
        @JsonProperty("title") String title,
        @JsonProperty("completed") boolean completed,
        @JsonProperty("order") int order
    ) {
        this.completed = completed;
        this.title = title;
        this.order = order;
    }
}
