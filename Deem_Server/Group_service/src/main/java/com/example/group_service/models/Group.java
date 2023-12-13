package com.example.group_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "di_group")
@Data
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

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

    @NotNull(message = "date_create must not be null")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_create")
    private Date date_create;

    @Column(name = "chat_id")
    private Long chat_id;

    @Transient
    private List<Long> users;
}
