package app.services;

import app.models.CreateTodo;
import app.models.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TodoService {

    private AtomicInteger atomicInteger = new AtomicInteger();
    private List<Todo> todos = new ArrayList<>();

    public List<Todo> findAll() {
        return todos;
    }

    public Todo find(int id) {
        return todos
            .stream()
            .filter(t -> t.id == id)
            .findFirst()
            .orElse(null);
    }

    public int add(CreateTodo todo) {
        int newId = atomicInteger.incrementAndGet();

        todos.add(new Todo(newId, todo.title));

        return newId;
    }

    public void delete(int id) {
        todos.removeIf(t -> t.id == id);
    }

    public void deleteAll() {
        todos.removeIf(t -> true);
    }
}
