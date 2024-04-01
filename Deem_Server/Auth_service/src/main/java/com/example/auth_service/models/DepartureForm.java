package com.example.auth_service.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DepartureForm {

    @NotNull
    private Long idAccount;

    @NotNull
    private int score;
}
