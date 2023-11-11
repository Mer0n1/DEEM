package com.example.administrative_service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
public class Account {
    private String username;
    private String name;
    private String surname;
    private String fathername;
}
