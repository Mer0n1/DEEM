package com.example.teacher_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "class")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "name must not be null")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "type must not be null")
    @Column(name = "type")
    private String type; //l - lecture, p - practice

    @NotEmpty(message = "place must not be null")
    @Column(name = "place")
    private String place; //room

    @NotNull(message = "date must not be null")
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
