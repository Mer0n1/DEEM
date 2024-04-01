package com.example.administrative_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "enrollment_form")
@Data
public class EnrollmentForm {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "faculty")
    private String faculty;

    @NotEmpty
    @Column(name = "department")
    private String department;

    @NotEmpty
    @Column(name = "past_school")
    private String past_school;

    @NotNull
    @Transient
    private Account account;
}
