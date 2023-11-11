package com.example.exam_service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Event")
@Data
public class Event {
    @Id
    private Long id;

    //private Exam exam;
}
