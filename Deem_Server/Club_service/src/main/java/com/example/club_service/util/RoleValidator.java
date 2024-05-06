package com.example.club_service.util;

import com.example.club_service.models.Account;
import com.example.club_service.services.ClubService;

public class RoleValidator {

    public static boolean validateCorrectRole(String role) {
        if (role == null || role.isEmpty())
            return false;

        if (!role.equals("PRESIDENT_LABOR") &&
            !role.equals("PRESIDENT_JOURNALISM") &&
            !role.equals("STUDENT"))
            return false;

        return true;
    }


    public static void validateCorrectRequest(Account account, String newRole) throws Exception {
        if (account.getId_club() == null)
            throw new Exception("Студент не состоит ни в каком в клубе");
        if (newRole.equals("PRESIDENT_LABOR") && account.getId_club() != ClubService.id_labor_club)
            throw new Exception("Студент не состоит в клубе труда");
        if (newRole.equals("PRESIDENT_JOURNALISM") && account.getId_club() != ClubService.id_journalism_club)
            throw new Exception("Студент не состоит в клубе журналистики");
    }

    public static boolean isStudentRole(String role) {
        if (role.equals("ROLE_STUDENT"))
            return true;
        else
            return false;
    }
}
