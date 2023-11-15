package com.example.administrative_service.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "enrollment_form")
@Data
public class EnrollmentForm {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "department")
    private String department;

    @Column(name = "past_school")
    private String past_school;
    //...

    @Transient
    private Account account;
}
