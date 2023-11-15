package com.example.administrative_service.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "group_creation_form")
@Data
public class GroupCreationForm {

    @Id
    @Column(name = "id")
    private Long id;

    @Transient
    private Group group;

    @Column(name = "department")
    private String department;
}
