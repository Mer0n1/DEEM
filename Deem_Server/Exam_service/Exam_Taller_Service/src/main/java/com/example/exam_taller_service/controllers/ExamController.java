package com.example.exam_taller_service.controllers;

import com.example.exam_taller_service.config.JWTFilter;
import com.example.exam_taller_service.models.MemberRequest;
import com.example.exam_taller_service.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/main")
public class ExamController {
    @Autowired
    private MainService service;

    @GetMapping("")
    public String get() {
        System.out.println("--");
        return "exam/main_site";
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'HIGH')")
    @PatchMapping("/update")
    public ResponseEntity<?> update() {

        //обновим список участников
        service.update();

        return ResponseEntity.ok().build();
    }

    /** Студсовет может добавлять участников */
    @PostMapping("/addMember")
    public ResponseEntity<?> addMember(@RequestBody MemberRequest memberRequest) {
        service.addMembers(memberRequest.getMembers());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deleteMember")
    public ResponseEntity<?> deleteMember(@RequestBody MemberRequest memberRequest) {
        System.out.println("addMembetr");
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
