package com.example.administrative_service.models;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class Event {
    private Long id;
    private String faculty;

    //private Exam exam;

    //Временно
    private String type;
    private String name;
    private String description;
}
