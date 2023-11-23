package com.example.group_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "di_group")
@Data
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private int id;

    @NotEmpty(message = "faculty must not be null")
    @Column(name = "faculty")
    private String faculty; //название факультета

    @NotNull(message = "Course must not be null")
    @Column(name = "course")
    private int course; //курс.

    @NotNull(message = "score must not be null")
    @Column(name = "score")
    private int score;

    @NotEmpty(message = "name must not be null")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "date_create must not be null")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_create")
    private Date date_create;


    //news
}
