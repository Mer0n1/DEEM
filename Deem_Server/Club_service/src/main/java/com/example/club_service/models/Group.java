package com.example.club_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class Group {

    private Long id;
    private String faculty;
    private int course;
    private String name;
    private Date date_create;
    private Long chat_id;
    private String type;
    private String description;
    private int score;
}
