package com.example.club_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "club")
@Data
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "id_group")
    private Long id_group;

    @NotNull
    @Column(name = "id_leader")
    private Long id_leader;

    //private String description;
}
