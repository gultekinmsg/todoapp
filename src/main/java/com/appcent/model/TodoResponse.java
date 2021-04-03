package com.appcent.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoResponse {
    private Long id;
    private String name;
    private Boolean isCompleted;
    @JsonFormat(pattern = "dd/MM/yyyy - HH:mm")
    private LocalDateTime dateTime;
}
