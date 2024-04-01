package com.example.exam_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "di_event")
@Data
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "faculty")
    private String faculty;

    @NotNull
    @Column(name = "course")
    private int course;

    //@Column(name = "publication_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publication_date;

    @NotNull
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date start_date;

    @NotNull
    @Column(name = "group_id")
    private Long idGroup;

    //private Exam exam;
    //Временно
    @NotEmpty
    @Column(name = "type")
    private String type;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "description")
    private String description;
}
