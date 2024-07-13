package com.example.exam_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.sql.Time;
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

    @Column(name = "name")
    private String name;

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

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "id_exam")
    private Exam exam;
}
