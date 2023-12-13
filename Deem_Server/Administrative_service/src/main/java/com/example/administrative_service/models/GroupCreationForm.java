package com.example.administrative_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "group_creation_form")
@Data
public class GroupCreationForm {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Group must not be null")
    @Transient
    private Group group;

    @NotEmpty(message = "Department must not be null")
    @Column(name = "department")
    private String department;
}
