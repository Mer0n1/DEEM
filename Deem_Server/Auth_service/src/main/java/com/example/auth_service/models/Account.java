package com.example.auth_service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
public class Account {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "fathername")
    private String fathername;

    @Column(name = "score")
    private int score;

    @Column(name = "group_id")
    private int group_id;

    @Transient //ignoring object by jpa
    private Group group;

    @Column(name = "ROLE")
    private String ROLE;

    public void addScore(int score) {
        this.score += score;
    }
}
