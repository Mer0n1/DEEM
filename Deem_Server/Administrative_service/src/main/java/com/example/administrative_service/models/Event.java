package com.example.administrative_service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Event {
    private Long id;

    //private Exam exam;

    //Временно
    @NotEmpty(message = "Type must not be null")
    private String type;
    @NotEmpty(message = "Name must not be null")
    private String name;
    @NotEmpty(message = "Description must not be null")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date publication_date;

    @NotNull(message = "Start Date must not be null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date start_date;

    @NotNull(message = "Id group must not be null")
    private Long idGroup;
    @NotEmpty(message = "Faculty must not be null")
    private String faculty;
    @NotNull(message = "course must not be null")
    private Integer course;
}
