package com.example.group_service.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "di_group")
@Data
@NoArgsConstructor
public class Group {

    @Id
    @Column(name = "group_id")
    private int id;

    @Column(name = "faculty")
    private String faculty; //название факультета

    @Column(name = "course")
    private int course; //курс.

    @Column(name = "score")
    private int score;

    @Column(name = "name")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_create")
    private Date date_create;

}
