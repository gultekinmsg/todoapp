package com.appcent.todoapp.controller;

import com.appcent.todoapp.entity.TodoStatus;
import com.appcent.todoapp.model.TodoRequest;
import com.appcent.todoapp.model.TodoResponse;
import com.appcent.todoapp.service.TodoAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TodoAppController {

    private final TodoAppService todoAppService;

    @Autowired
    public TodoAppController(TodoAppService todoAppService) {
        this.todoAppService = todoAppService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createTodo(@RequestBody @Valid TodoRequest todoRequest) {
        todoAppService.createTodo(todoRequest);
    }

    @DeleteMapping("/{todoId}")
    public void deleteTodo(@PathVariable Long todoId) {
        todoAppService.deleteTodo(todoId);
    }

    @PutMapping("/{todoId}")
    public void changeStatusTodo(@PathVariable Long todoId) {
        todoAppService.changeStatusTodo(todoId);
    }

    @GetMapping
    public List<TodoResponse> getTodos(@RequestParam(name = "status") TodoStatus todoStatus) {
        if (todoStatus == TodoStatus.COMPLETED) {
            return todoAppService.getCompletedTodos();
        } else if (todoStatus == TodoStatus.WAITING) {
            return todoAppService.getWaitingTodos();
        } else {
            return todoAppService.getAllTodos();
        }
    }
}
