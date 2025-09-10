package com.example.exam_taller_service.controllers;

import com.example.exam_taller_service.config.JWTFilter;
import com.example.exam_taller_service.config.PersonDetails;
import com.example.exam_taller_service.models.MemberRequest;
import com.example.exam_taller_service.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/main")
public class ExamController {
    @Autowired
    private MainService service;


    /** Получить страничку экзамена*/
    @CrossOrigin(origins = "*")
    @GetMapping("")
    public String get(@AuthenticationPrincipal PersonDetails personDetails) {
        System.out.println("-- " + personDetails.getUsername());
        return "exam/main_site";
    }

    @GetMapping("/site2")
    public String site2() {
        System.out.println("---");
        return "exam/test2_site";
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'HIGH', 'PRESIDENT_COUNCIL')")
    @PatchMapping("/update")
    public ResponseEntity<?> update() {

        //обновим список участников
        service.update();

        return ResponseEntity.ok().build();
    }

    /** Студсовет может добавлять участников */
    @PreAuthorize("hasAnyRole('ADMIN', 'HIGH', 'PRESIDENT_COUNCIL')")
    @PostMapping("/addMember")
    public ResponseEntity<?> addMember(@RequestBody MemberRequest memberRequest) {
        service.addMembers(memberRequest.getMembers());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HIGH', 'PRESIDENT_COUNCIL')")
    @PostMapping("/deleteMember")
    public ResponseEntity<?> deleteMember(@RequestBody MemberRequest memberRequest) {
        System.out.println("addMember");
        service.deleteMember(memberRequest.getMembers());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/enable")
    public ResponseEntity<?> enable() {
        JWTFilter.isDisable = false;
        return ResponseEntity.ok().build();
    }

    @PostMapping("/disable")
    public ResponseEntity<?> disable() {
        JWTFilter.isDisable = true;
        System.out.println("disable");
        return ResponseEntity.ok().build();
    }

}
