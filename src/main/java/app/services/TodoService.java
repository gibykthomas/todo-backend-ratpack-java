package app.services;

import app.models.CreateTodo;
import app.models.Todo;
import app.models.UpdateTodo;

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

        todos.add(new Todo(newId, todo.title, todo.order, false));

        return newId;
    }

    public void update(int id, UpdateTodo updateTodo) {
        todos
            .stream()
            .filter(t1 -> t1.id == id)
            .findFirst()
            .ifPresent(t -> {
                todos.remove(t);
                todos.add(
                    new Todo(
                        t.id,
                        updateTodo.title,
                        updateTodo.order,
                        updateTodo.completed
                    )
                );
            });
    }

    public void delete(int id) {
        todos.removeIf(t -> t.id == id);
    }

    public void deleteAll() {
        todos.removeIf(t -> true);
    }
}
