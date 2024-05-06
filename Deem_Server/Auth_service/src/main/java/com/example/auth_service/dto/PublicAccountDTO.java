package com.example.auth_service.dto;

import com.example.auth_service.models.Group;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class PublicAccountDTO {

    private Long id;
    private Long id_club;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String fathername;
    private int score;
    private Long group_id;
    private Group group;
    private String role;
}
