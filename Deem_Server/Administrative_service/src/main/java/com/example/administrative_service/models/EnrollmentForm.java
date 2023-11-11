package com.example.administrative_service.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
//@Table
@Data
public class EnrollmentForm {

    @Id
    private Long id;

    private String faculty;
    private String department;
    private String past_school;
    //...

    @Transient
    private Account account;
}
