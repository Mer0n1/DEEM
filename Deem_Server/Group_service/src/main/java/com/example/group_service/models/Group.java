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

    @NotEmpty
    @Column(name = "faculty")
    private String faculty;

    @NotNull
    @Column(name = "course")
    private int course; //курс

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_create")
    private Date date_create;

    @Column(name = "chat_id")
    private Long chat_id;

    @Column(name = "type")
    private String type;

    @NotNull
    @Transient
    private int score;

    @Transient
    private List<Long> users;
}
