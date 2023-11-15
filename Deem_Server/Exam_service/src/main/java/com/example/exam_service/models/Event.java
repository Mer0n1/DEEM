package com.example.exam_service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "event")
@Data
public class Event {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "faculty")
    private String faculty;

    //private Exam exam;

    //Временно
    @Column(name = "type")
    private String type;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
}
