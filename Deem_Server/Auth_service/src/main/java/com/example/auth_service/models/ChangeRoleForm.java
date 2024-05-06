package com.example.auth_service.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeRoleForm {

    @NotNull
    private Long id_student;
    @NotEmpty
    private String newRole;
}
