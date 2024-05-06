package com.example.club_service.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student_studcouncil")
public class StudentCouncil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_account")
    private Long idAccount;

    @Column(name = "type")
    private String type;
}
