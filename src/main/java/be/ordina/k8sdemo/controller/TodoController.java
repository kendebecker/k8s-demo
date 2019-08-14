package be.ordina.k8sdemo.controller;

import be.ordina.k8sdemo.model.Todo;
import be.ordina.k8sdemo.services.TodoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class TodoController {

    @Value("${demo.base-path}")
    private String basePath;

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        List<Todo> todoList = todoService.getTodos();
        model.addAttribute("todoList", todoList);
        model.addAttribute("newTodo", new Todo());
        model.addAttribute("basePath", basePath);
        return "index";
    }

    @RequestMapping("add")
    public String addTodo(@ModelAttribute Todo todo) {
        todoService.addTodo(todo);
        return "redirect:" + basePath;
    }
}
