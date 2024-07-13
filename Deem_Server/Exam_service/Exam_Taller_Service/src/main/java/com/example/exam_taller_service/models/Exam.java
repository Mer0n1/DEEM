package com.example.exam_taller_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.sql.Time;
import java.util.Date;

@Data
@ToString
public class Exam {

    private Long id;
    private String faculty;
    private int course;
    private Date publication_date;
    private Date start_date;
    private Long idGroup;
    private String type;
    private String name;
    private String description;
    private String AddressToExamService;
    private Time duration;
}
