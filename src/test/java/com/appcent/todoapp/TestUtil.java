package com.appcent.todoapp;

import com.appcent.todoapp.entity.Todo;
import com.appcent.todoapp.model.TodoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestUtil {
    private TestUtil() {
    }

    protected static TodoRequest getCompleteTodoRequest() {
        TodoRequest todoRequest = new TodoRequest();
        todoRequest.setName("Complete todoRequest");
        todoRequest.setIsCompleted(true);
        return todoRequest;
    }

    protected static TodoRequest getWaitingTodoRequest() {
        TodoRequest todoRequest = new TodoRequest();
        todoRequest.setName("Waiting todoRequest");
        todoRequest.setIsCompleted(true);
        return todoRequest;
    }

    protected static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected static List<Todo> getAllTodoList() {
        List<Todo> allTodoList = new ArrayList<>();
        allTodoList.add(setCompletedTodo());
        allTodoList.add(setWaitingTodo());
        return allTodoList;
    }

    protected static List<Todo> getCompletedTodoList() {
        List<Todo> completedTodoList = new ArrayList<>();
        completedTodoList.add(setCompletedTodo());
        return completedTodoList;
    }

    protected static List<Todo> getWaitingTodoList() {
        List<Todo> waitingTodoList = new ArrayList<>();
        waitingTodoList.add(setWaitingTodo());
        return waitingTodoList;
    }

    protected static Todo setCompletedTodo() {
        Todo todo = new Todo();
        todo.setName("Complete project");
        todo.setIsCompleted(true);
        todo.setLocalDateTime(LocalDateTime.now());
        return todo;
    }

    protected static Todo setWaitingTodo() {
        Todo todo = new Todo();
        todo.setName("Waiting project");
        todo.setIsCompleted(false);
        todo.setLocalDateTime(LocalDateTime.now());
        return todo;
    }
}
