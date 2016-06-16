package app.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateTodo {

    public final String title;

    public CreateTodo(@JsonProperty("title") String title) {
        this.title = title;
    }
}
