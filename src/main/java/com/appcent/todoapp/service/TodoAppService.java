package com.appcent.todoapp.service;

import com.appcent.todoapp.entity.Todo;
import com.appcent.todoapp.entity.TodoStatus;
import com.appcent.todoapp.mapper.TodoAppMapper;
import com.appcent.todoapp.model.TodoRequest;
import com.appcent.todoapp.model.TodoResponse;
import com.appcent.todoapp.repository.TodoAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TodoAppService {

    private final TodoAppRepository todoAppRepository;

    @Autowired
    public TodoAppService(TodoAppRepository todoAppRepository) {
        this.todoAppRepository = todoAppRepository;
    }

    public void createTodo(TodoRequest todoRequest) {
        Todo todo = TodoAppMapper.toEntity(todoRequest);
        todoAppRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        Todo todoToDelete = getTodo(id);
        checkExist(todoToDelete, id);
        todoAppRepository.delete(todoToDelete);
    }

    public void changeStatusTodo(Long id) {
        Todo todoToChange = getTodo(id);
        checkExist(todoToChange, id);
        todoToChange.setIsCompleted(!todoToChange.getIsCompleted());
        todoAppRepository.save(todoToChange);
    }

    public List<TodoResponse> getAllTodos() {
        List<Todo> todoList = todoAppRepository.findAll();
        checkEmpty(todoList, TodoStatus.ALL);
        return TodoAppMapper.toModels(todoList);
    }

    public List<TodoResponse> getCompletedTodos() {
        List<Todo> todoList = todoAppRepository.findByIsCompletedOrderByLocalDateTimeDesc(true);
        checkEmpty(todoList, TodoStatus.COMPLETED);
        return TodoAppMapper.toModels(todoList);
    }

    public List<TodoResponse> getWaitingTodos() {
        List<Todo> todoList = todoAppRepository.findByIsCompletedOrderByLocalDateTimeDesc(false);
        checkEmpty(todoList, TodoStatus.WAITING);
        return TodoAppMapper.toModels(todoList);
    }

    private void checkEmpty(List<Todo> todoList, TodoStatus todoStatus) {
        if (todoList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There are not any " + todoStatus + " todo items");
        }
    }

    private Todo getTodo(Long id) {
        return todoAppRepository.findTodoById(id);
    }

    private void checkExist(Todo todo, Long id) {
        if (todo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo item with id:" + id + " not exist");
        }
    }
}
