package com.example.club_service.util;

import com.example.club_service.models.Account;
import com.example.club_service.models.ClubForm;
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

    public static boolean checkForAddingSCOUNCIL(String role) {
        if (role.equals("ROLE_PRESIDENT_COUNCIL") ||
            role.equals("ROLE_ADMIN") ||
            role.equals("ROLE_TEACHER"))
            return false;
        else
            return true;
    }

}
