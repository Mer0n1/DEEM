package com.example.club_service.models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Account {
    private Long id;
    private String username;
    private String name;
    private String role;
    private Long group_id;
    private Long id_club;
}
