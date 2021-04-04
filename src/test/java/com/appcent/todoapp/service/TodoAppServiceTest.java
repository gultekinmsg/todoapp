package com.appcent.todoapp.service;

import com.appcent.todoapp.TestUtil;
import com.appcent.todoapp.entity.Todo;
import com.appcent.todoapp.mapper.TodoAppMapper;
import com.appcent.todoapp.model.TodoResponse;
import com.appcent.todoapp.repository.TodoAppRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TodoAppServiceTest {
    @MockBean
    private TodoAppRepository todoAppRepository;

    @Autowired
    private TodoAppService todoAppService;

    @Test
    void createTodo_GivenRequestHasApprovedWhenTryToCreateTodoThenShouldCreateSuccessfully() {
        ResponseStatusException expectedException = null;
        try {
            todoAppService.createTodo(TestUtil.getWaitingTodoRequest());
        } catch (ResponseStatusException ex) {
            expectedException = ex;
        }
        assertNull(expectedException);
    }

    @Test
    void deleteTodo_GivenRequestHasReturnInDbWhileTryDeleteTodoThenShouldDelete() {
        when(todoAppRepository.findTodoById(1L)).thenReturn(TestUtil.setWaitingTodo());
        ResponseStatusException expectedException = null;
        try {
            todoAppService.deleteTodo(1L);
        } catch (ResponseStatusException ex) {
            expectedException = ex;
        }
        assertNull(expectedException);
    }

    @Test
    void deleteTodo_GivenRequestHasNotReturnInDbWhileTryDeleteTodoThenShouldThrowNotFoundException() {
        when(todoAppRepository.findTodoById(1L)).thenReturn(null);
        ResponseStatusException expectedException = null;
        try {
            todoAppService.deleteTodo(1L);
        } catch (ResponseStatusException ex) {
            expectedException = ex;
        }
        assertNotNull(expectedException);
    }

    @Test
    void changeStatus_TodoGivenRequestHasReturnInDbWhileTryChangeStatusTodoThenShouldDelete() {
        when(todoAppRepository.findTodoById(1L)).thenReturn(TestUtil.setCompletedTodo());
        ResponseStatusException expectedException = null;
        try {
            todoAppService.changeStatusTodo(1L);
        } catch (ResponseStatusException ex) {
            expectedException = ex;
        }
        assertNull(expectedException);
    }

    @Test
    void changeStatus_TodoGivenRequestHasNotReturnInDbWhileTryChangeStatusThenShouldThrowNotFoundException() {
        when(todoAppRepository.findTodoById(1L)).thenReturn(null);
        ResponseStatusException expectedException = null;
        try {
            todoAppService.changeStatusTodo(1L);
        } catch (ResponseStatusException ex) {
            expectedException = ex;
        }
        assertNotNull(expectedException);
    }

    @Test
    void getAllTodos_TodosExistInDbWhenTryToGetAllBillsThenShouldReturnAllTodos() {
        List<Todo> todoLists = TestUtil.getAllTodoList();
        when(todoAppRepository.findAll()).thenReturn(todoLists);
        List<TodoResponse> todoList = todoAppService.getAllTodos();
        assertEquals(todoList, TodoAppMapper.toModels(todoLists));
    }

    @Test
    void getAllTodos_TodosNotExistInDbWhenTryToGetAllBillsThenShoulThrowNotFoundException() {
        when(todoAppRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseStatusException expectedException = null;
        try {
            todoAppService.getAllTodos();
        } catch (ResponseStatusException ex) {
            expectedException = ex;
        }
        assertNotNull(expectedException);
    }

    @Test
    void getCompletedTodos_CompletedTodosExistInDbWhenTryToGetCompletedBillsThenShouldReturnCompletedTodos() {
        List<Todo> todoLists = TestUtil.getCompletedTodoList();
        when(todoAppRepository.findByIsCompletedOrderByLocalDateTimeDesc(true)).thenReturn(todoLists);
        List<TodoResponse> todoList = todoAppService.getCompletedTodos();
        assertEquals(todoList, TodoAppMapper.toModels(todoLists));
    }

    @Test
    void getCompletedTodos_CompletedTodosNotExistInDbWhenTryToGetCompletedBillsThenShouldThrowNotFoundException() {
        when(todoAppRepository.findByIsCompletedOrderByLocalDateTimeDesc(true)).thenReturn(new ArrayList<>());
        ResponseStatusException expectedException = null;
        try {
            todoAppService.getCompletedTodos();
        } catch (ResponseStatusException ex) {
            expectedException = ex;
        }
        assertNotNull(expectedException);
    }

    @Test
    void getWaitingTodos_WaitingTodosExistInDbWhenTryToGetWaitingBillsThenShouldReturnWaitingTodos() {
        List<Todo> todoLists = TestUtil.getWaitingTodoList();
        when(todoAppRepository.findByIsCompletedOrderByLocalDateTimeDesc(false)).thenReturn(todoLists);
        List<TodoResponse> todoList = todoAppService.getWaitingTodos();
        assertEquals(todoList, TodoAppMapper.toModels(todoLists));
    }

    @Test
    void getWaitingTodos_WaitingTodosNotExistInDbWhenTryToGetWaitingBillsThenShouldThrowNotFoundException() {
        when(todoAppRepository.findByIsCompletedOrderByLocalDateTimeDesc(false)).thenReturn(new ArrayList<>());
        ResponseStatusException expectedException = null;
        try {
            todoAppService.getWaitingTodos();
        } catch (ResponseStatusException ex) {
            expectedException = ex;
        }
        assertNotNull(expectedException);
    }


}
