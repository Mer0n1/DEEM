package com.example.administrative_service.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
//@Table
@Data
public class GroupCreationForm {

    @Id
    private Long id;

    @Transient
    private Group group;

    private String department;
}
