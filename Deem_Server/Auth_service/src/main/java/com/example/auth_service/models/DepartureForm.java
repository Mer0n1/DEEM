package com.example.auth_service.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DepartureForm {

    @NotNull(message = "Некорректно заполнено")
    private Long idAccount;

    @NotNull(message = "Отсутствует указанные очки")
    private int score;
}
