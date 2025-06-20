package com.example.club_service.util;

import com.example.club_service.models.Club;
import com.example.club_service.models.ClubForm;
import com.example.club_service.repositories.ClubRepository;
import com.example.club_service.services.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ClubValidator {

    @Autowired
    private ClubRepository clubRepository;

    public boolean validate(String name) {
        if (name == null || name.isEmpty())
            return false;

        return !clubRepository.findByName(name).isEmpty();
    }


    /*@Override
    public void validate(Object target, Errors errors) {
        Club club = (Club) target;

        if (!clubRepository.findById_group(club.getId_group()).isEmpty())
            errors.rejectValue("id_group", "", "Такой клуб уже существует. Такая группа уже занята.");
    }*/


}
