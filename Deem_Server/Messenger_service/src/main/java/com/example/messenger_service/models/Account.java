package com.example.messenger_service.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account {

    private String username;
    private String password;
    private String name;
    private String surname;
    private String fathername;
    private int score;
}
