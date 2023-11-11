package com.example.administrative_service.controllers;

import com.example.administrative_service.config.PersonDetails;
import com.example.administrative_service.models.*;
import com.example.administrative_service.services.RestTemplateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


//@PreAuthorize("hasAnyRole('ADMIN', 'HIGH')")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RestTemplateService restTemplateService;

    /** Исключение студента. Удаление из группы */
    @DeleteMapping("/expelStudent")
    public void expelStudent(@RequestBody ExclusionForm form) {

    }

    @PatchMapping("/transferStudent")
    public void transferStudent(@RequestBody TransferForm form) {

    }

    @PostMapping("/createStudent")
    public void createStudent(@RequestBody EnrollmentForm form) {

    }

    @PostMapping("/createGroup")
    public void createGroup(@RequestBody GroupCreationForm form) {

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/sendScore")
    public ResponseEntity<Void> sendScore(@RequestBody @Valid DepartureForm form,
                                          BindingResult bindingResult) throws JsonProcessingException {
        System.out.println("sendScore");

        if (bindingResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        restTemplateService.sendScore(form);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /** Выпуск ивента (экзамена) */
    @PostMapping("/releaseEvent")
    public void releaseEvent(@RequestBody Event event) {

    }

}

