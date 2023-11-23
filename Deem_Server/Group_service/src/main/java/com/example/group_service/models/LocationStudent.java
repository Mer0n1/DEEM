package com.example.group_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationStudent {
    private Long idStudent;
    private String faculty;
    private int course;
}
