package com.example.statistics_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class SubmissionForm {

    private Long id;
    private Long idAccount;
    private int score;
    private String description;
    private Date date;
}
