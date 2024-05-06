package com.example.club_service.controllers;

import com.example.club_service.config.PersonDetails;
import com.example.club_service.models.*;
import com.example.club_service.services.ClubService;
import com.example.club_service.services.RestTemplateService;
import com.example.club_service.services.StudentCouncilService;
import com.example.club_service.util.ClubValidator;
import com.example.club_service.util.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/club")
public class ClubController {

    @Autowired
    private ClubService clubService;
    @Autowired
    private ClubValidator clubValidator;
    @Autowired
    private RestTemplateService restTemplateService;
    @Autowired
    private StudentCouncilService studentCouncilService;

    @GetMapping("/getClubs")
    public List<Club> getClubs() {
        return clubService.getClubs();
    }

    @PreAuthorize("hasRole('PRESIDENT_COUNCIL')")
    @PostMapping("/addClub")
    public ResponseEntity<?> addClub(@RequestParam("name") String name, @RequestParam("shortName") String shortName,
                                     @AuthenticationPrincipal PersonDetails personDetails) {

        if (clubValidator.validate(name))
            return ResponseEntity.badRequest().body("Такое название существует");

        //создание группы
        ResponseEntity<?> response = restTemplateService.createGroup(shortName);
        if (response.getStatusCode() == HttpStatusCode.valueOf(400))
            return response;

        //создание клуба
        clubService.createClub(name, ((Group) response.getBody()).getId(), personDetails.getId());

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('PRESIDENT_COUNCIL')")
    @PostMapping("/deleteClub")
    public ResponseEntity<?> deleteClub(@RequestParam("name") String name) {
        Club club = clubService.getClub(name);

        if (club == null)
            return ResponseEntity.badRequest().body("Такого клуба не существует");
        else
            clubService.deleteClub(club);

        restTemplateService.deleteGroup(club.getId_group());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('PRESIDENT_COUNCIL')") //TODO also president other clubs with the rights
    @PostMapping("/addStudentToClub")
    public ResponseEntity<?> addStudentToClub(@RequestBody ClubForm form) {
        return restTemplateService.addStudentToClub(form);
    }

    @PreAuthorize("hasRole('PRESIDENT_COUNCIL')")
    @PostMapping("/expelStudentClub")
    public ResponseEntity<?> ExpelStudentClub(@RequestBody ClubForm form) {
        return restTemplateService.expelStudentClub(form);
    }

    @PreAuthorize("hasRole('PRESIDENT_COUNCIL')")
    @PostMapping("/addStudentCouncil")
    public ResponseEntity<?> addStudentCouncil(@RequestParam("id_account") Long id_account) {
        //SCOUNCIL присуждается автоматически после принятия студента в студсовет и не контролируется текущим методом ChangeRole

        try {
            Account account = restTemplateService.getAccount(id_account);

            if (!RoleValidator.isStudentRole(account.getRole()))
                throw new Exception("Начальная роль должна быть STUDENT");

            studentCouncilService.addNewMember(account);

            return restTemplateService.ChangeRole(new ChangeRoleForm(account.getId(), "SCOUNCIL")); //kafka
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('PRESIDENT_COUNCIL')")
    @PostMapping("/expelStudentCouncil")
    public ResponseEntity<?> ExpelStudentCouncil(@RequestParam("id_account") Long id_account) {

        try {
            StudentCouncil student = studentCouncilService.getStudentCouncil(id_account);
            studentCouncilService.delete(student);

            return restTemplateService.ChangeRole(new ChangeRoleForm(student.getId(), "STUDENT")); //kafka
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('PRESIDENT_COUNCIL','HIGH')")
    @PostMapping("/ChangeRole")
    public ResponseEntity<?> ChangeRole(@RequestBody ChangeRoleForm form) {

        if (!RoleValidator.validateCorrectRole(form.getNewRole()))
            return ResponseEntity.badRequest().body("Некорректная роль");

        try {
            Account account = restTemplateService.getAccount(form.getId_student());
            RoleValidator.validateCorrectRequest(account, form.getNewRole());

            return restTemplateService.ChangeRole(form); //kafka
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

