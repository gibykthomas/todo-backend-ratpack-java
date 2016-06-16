package app.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateTodo {

    public final String title;
    public final boolean completed;

    public UpdateTodo(
        @JsonProperty("title") String title,
        @JsonProperty("completed") boolean completed
    ) {
        this.completed = completed;
        this.title = title;
    }
}
