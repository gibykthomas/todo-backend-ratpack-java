package app.models;

public class Todo {

    public final int id;
    public final String title;
    public final boolean completed;

    public Todo(int id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }
}
