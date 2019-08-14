package be.ordina.k8sdemo.services;

import be.ordina.k8sdemo.model.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    private List<Todo> todoList;

    public TodoService() {
        this.todoList = new ArrayList<>();
    }

    public List<Todo> getTodos() {
        return todoList;
    }

    public Todo addTodo(Todo todo) {
        todoList.add(todo);
        return todo;
    }
}
