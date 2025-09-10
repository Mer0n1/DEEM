package com.example.exam_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

import java.sql.Time;

@Entity
@Table(name = "exam")
@Data
@ToString
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @NotEmpty
    @Column(name = "duration")
    private Time duration;

    @Column(name = "address")
    private String addressToExamService;

}
