package app.models;

public class Todo {

    public final int id;
    public final String title;
    public final int order;
    public final boolean completed;

    public Todo(int id, String title, int order, boolean completed) {
        this.id = id;
        this.title = title;
        this.order = order;
        this.completed = completed;
    }

    public WithHref<Todo> withHref(String baseUrl) {
        return new WithHref<>(this, baseUrl + "/" + this.id);
    }
}
