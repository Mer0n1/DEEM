package com.example.club_service.models;

import lombok.Data;

@Data
public class ChangeRoleForm {
    private Long id_student;
    private String newRole;

    public ChangeRoleForm(Long id_student, String newRole) {
        this.id_student = id_student;
        this.newRole = newRole;
    }
}
