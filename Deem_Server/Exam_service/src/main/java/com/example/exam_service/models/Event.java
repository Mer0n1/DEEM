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

    @NotEmpty(message = "faculty must not be null")
    @Column(name = "faculty")
    private String faculty;

    //@Column(name = "publication_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publication_date;

    @NotNull(message = "start_date must not be null")
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date start_date;

    @NotNull(message = "Id Group must not be null")
    @Column(name = "group_id")
    private Long idGroup;

    //private Exam exam;
    //Временно
    @NotEmpty(message = "type must not be null")
    @Column(name = "type")
    private String type;

    @NotEmpty(message = "name must not be null")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Description must not be null")
    @Column(name = "description")
    private String description;
}
