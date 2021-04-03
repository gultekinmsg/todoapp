package com.appcent.mapper;

import com.appcent.entity.Todo;
import com.appcent.model.TodoRequest;
import com.appcent.model.TodoResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TodoAppMapper {

    private TodoAppMapper() {
    }

    public static Todo toEntity(TodoRequest todoRequest) {
        Todo todo = new Todo();
        todo.setName(todoRequest.getName());
        todo.setIsCompleted(todoRequest.getIsCompleted());
        todo.setLocalDateTime(LocalDateTime.now());
        return todo;
    }

    public static List<TodoResponse> toModels(List<Todo> todos) {
        List<TodoResponse> todoResponses = new ArrayList<>();
        todos.forEach(todo -> todoResponses.add(toModel(todo)));
        return todoResponses;
    }

    private static TodoResponse toModel(Todo todo) {
        TodoResponse todoResponse = new TodoResponse();
        todoResponse.setId(todo.getId());
        todoResponse.setName(todo.getName());
        todoResponse.setIsCompleted(todo.getIsCompleted());
        todoResponse.setDateTime(todo.getLocalDateTime());
        return todoResponse;
    }
}
