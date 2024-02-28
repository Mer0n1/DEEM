package com.example.club_service.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "club")
@Data
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "id_group")
    private Long id_group;

    @Column(name = "id_leader")
    private Long id_leader;

    //private String description;
}
