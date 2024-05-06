package com.example.auth_service.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClubForm {
    @NotNull
    private Long id_student;
    @NotNull
    @Min(1)
    private Long id_club;
}
