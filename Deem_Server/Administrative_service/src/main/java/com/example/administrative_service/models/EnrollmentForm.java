package com.example.administrative_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Table(name = "enrollment_form")
@Data
public class EnrollmentForm {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Faculty must not be null")
    @Column(name = "faculty")
    private String faculty;

    @NotEmpty(message = "Department must not be null")
    @Column(name = "department")
    private String department;

    @NotEmpty(message = "Past school must not be null")
    @Column(name = "past_school")
    private String past_school;

    @NotEmpty(message = "Account must not be null")
    @Transient
    private Account account;
}
