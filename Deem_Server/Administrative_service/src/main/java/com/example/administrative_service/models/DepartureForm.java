package com.example.administrative_service.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DepartureForm {
    /*@NotEmpty(message = "Некорректно заполнено")
    private Account account;*/
    @NotNull(message = "Некорректно заполнено")
    private Long idAccount;

    @NotNull(message = "Отсутствует указанные очки")
    private int score;
}
