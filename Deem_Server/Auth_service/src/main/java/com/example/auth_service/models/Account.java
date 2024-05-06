package com.example.auth_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@ToString
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "username")
    private String username;

    @NotEmpty
    @Column(name = "password")
    private String password;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "surname")
    private String surname;

    @Column(name = "fathername")
    private String fathername;

    @Column(name = "score")
    private int score;

    @NotNull
    @Column(name = "group_id")
    private Long group_id;

    @Transient //ignoring object by jpa
    private Group group;

    @NotEmpty
    @Column(name = "role")
    private String role;

    @Column(name = "id_club")
    private Long id_club;

    public void addScore(int score) {
        this.score += score;
    }
}
