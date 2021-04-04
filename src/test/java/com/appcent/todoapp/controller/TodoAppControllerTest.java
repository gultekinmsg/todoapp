package com.appcent.todoapp.controller;

import com.appcent.todoapp.TestUtil;
import com.appcent.todoapp.entity.Todo;
import com.appcent.todoapp.mapper.TodoAppMapper;
import com.appcent.todoapp.model.TodoRequest;
import com.appcent.todoapp.model.TodoResponse;
import com.appcent.todoapp.service.TodoAppService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TodoAppControllerTest {
    @MockBean
    TodoAppService todoAppService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void createTodo() throws Exception {
        TodoRequest todoRequest = TestUtil.getWaitingTodoRequest();
        mockMvc.perform(post("/").content(TestUtil.asJsonString(todoRequest)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void changeStatusTodo() throws Exception {
        Todo todoToChange = TestUtil.setWaitingTodo();
        Long todoId = 1L;
        mockMvc.perform(put("/{todoId}", todoId).content(TestUtil.asJsonString(todoToChange)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTodo() throws Exception {
        Todo todoToDelete = TestUtil.setWaitingTodo();
        Long todoId = 1L;
        mockMvc.perform(delete("/{todoId}", todoId).content(TestUtil.asJsonString(todoToDelete)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllTodos() throws Exception {
        List<TodoResponse> todoResponseList = TodoAppMapper.toModels(TestUtil.getAllTodoList());
        when(todoAppService.getAllTodos()).thenReturn(todoResponseList);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        mockMvc.perform(get("/?status=ALL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(todoResponseList.size())))
                .andExpect(jsonPath("$[0].id").value(todoResponseList.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(todoResponseList.get(0).getName()))
                .andExpect(jsonPath("$[0].isCompleted").value(todoResponseList.get(0).getIsCompleted()))
                .andExpect(jsonPath("$[0].dateTime").value(todoResponseList.get(0).getDateTime().format(dateTimeFormatter)));
    }

    @Test
    void getCompletedTodos() throws Exception {
        List<TodoResponse> todoResponseList = TodoAppMapper.toModels(TestUtil.getCompletedTodoList());
        when(todoAppService.getCompletedTodos()).thenReturn(todoResponseList);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        mockMvc.perform(get("/?status=COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(todoResponseList.size())))
                .andExpect(jsonPath("$[0].id").value(todoResponseList.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(todoResponseList.get(0).getName()))
                .andExpect(jsonPath("$[0].isCompleted").value(todoResponseList.get(0).getIsCompleted()))
                .andExpect(jsonPath("$[0].dateTime").value(todoResponseList.get(0).getDateTime().format(dateTimeFormatter)));
    }

    @Test
    void getWaitingTodos() throws Exception {
        List<TodoResponse> todoResponseList = TodoAppMapper.toModels(TestUtil.getWaitingTodoList());
        when(todoAppService.getWaitingTodos()).thenReturn(todoResponseList);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        mockMvc.perform(get("/?status=WAITING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(todoResponseList.size())))
                .andExpect(jsonPath("$[0].id").value(todoResponseList.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(todoResponseList.get(0).getName()))
                .andExpect(jsonPath("$[0].isCompleted").value(todoResponseList.get(0).getIsCompleted()))
                .andExpect(jsonPath("$[0].dateTime").value(todoResponseList.get(0).getDateTime().format(dateTimeFormatter)));
    }
}
