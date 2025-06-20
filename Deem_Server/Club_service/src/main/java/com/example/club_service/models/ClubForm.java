package com.example.club_service.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClubForm {
    @NotNull
    private Long id_student;
    @NotNull
    private Long id_club;
}
