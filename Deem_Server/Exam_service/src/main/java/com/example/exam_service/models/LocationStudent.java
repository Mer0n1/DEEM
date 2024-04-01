package com.example.exam_service.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationStudent {

    @NotNull()
    private Long idStudent;

    @NotEmpty
    private String faculty;

    @NotNull
    private int course;
}
