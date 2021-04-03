package com.appcent.todoapp.repository;

import com.appcent.todoapp.entity.Todo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoAppRepository extends CrudRepository<Todo, Long> {
    List<Todo> findAll();

    List<Todo> findByIsCompletedOrderByLocalDateTimeDesc(Boolean isCompleted);

    Todo findTodoById(Long id);
}
