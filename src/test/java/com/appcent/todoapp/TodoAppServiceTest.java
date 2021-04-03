package com.appcent.todoapp;

import com.appcent.todoapp.mapper.TodoAppMapper;
import com.appcent.todoapp.model.TodoResponse;
import com.appcent.todoapp.repository.TodoAppRepository;
import com.appcent.todoapp.service.TodoAppService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class TodoAppServiceTest {
    @MockBean
    private TodoAppRepository todoAppRepository;

    @Autowired
    private TodoAppService todoAppService;

    @Test
    void createTodo_GivenRequestHasApprovedWhenTryToCreateTodoThenShouldCreateSuccessfully() {

    }

    @Test
    void deleteTodo_GivenRequestHasReturnInDbWhileTryDeleteTodoThenShouldDelete() {

    }

    @Test
    void deleteTodo_GivenRequestHasNotReturnInDbWhileTryDeleteTodoThenShouldThrowNotFoundException() {

    }

    @Test
    void changeStatus_TodoGivenRequestHasReturnInDbWhileTryChangeStatusTodoThenShouldDelete() {

    }

    @Test
    void changeStatus_TodoGivenRequestHasNotReturnInDbWhileTryChangeStatusThenShouldThrowNotFoundException() {
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
    void getAllTodos_TodosExistInDbWhenTryToGetAllBillsThenShouldReturnAllTodos() {
        when(todoAppRepository.findAll()).thenReturn(TestUtil.getAllTodoList());
        List<TodoResponse> todoList = todoAppService.getAllTodos();
        assertEquals(todoList, TodoAppMapper.toModels(TestUtil.getAllTodoList()));
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
        when(todoAppRepository.findByIsCompletedOrderByLocalDateTimeDesc(true)).thenReturn(TestUtil.getCompletedTodoList());
        List<TodoResponse> todoList = todoAppService.getCompletedTodos();
        assertEquals(todoList, TodoAppMapper.toModels(TestUtil.getCompletedTodoList()));
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
        when(todoAppRepository.findByIsCompletedOrderByLocalDateTimeDesc(false)).thenReturn(TestUtil.getWaitingTodoList());
        List<TodoResponse> todoList = todoAppService.getWaitingTodos();
        assertEquals(todoList, TodoAppMapper.toModels(TestUtil.getWaitingTodoList()));
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
