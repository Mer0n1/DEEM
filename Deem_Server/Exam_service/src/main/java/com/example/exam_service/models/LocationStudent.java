package com.example.exam_service.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationStudent {

    @NotNull()
    private Long idStudent;

    @NotEmpty(message = "faculty must not be null")
    private String faculty;

    @NotNull(message = "Username must not be null")
    private int course;
}
