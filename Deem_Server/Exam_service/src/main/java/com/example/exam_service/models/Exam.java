package com.example.exam_service.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Exam")
@Data
public class Exam {
    @Id
    private Long id;

    private String type;
    private String description;

}
