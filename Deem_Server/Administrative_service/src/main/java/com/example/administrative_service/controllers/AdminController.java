package com.example.administrative_service.controllers;

import com.example.administrative_service.models.*;
import com.example.administrative_service.services.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ADMIN', 'HIGH')")
public class AdminController {

    @Autowired
    private RestTemplateService restTemplateService;
    @Autowired
    private GroupCreationFormService groupCreationFormService;
    @Autowired
    private EnrollmentStoryService enrollmentStoryService;
    @Autowired
    private TransferStoryService transferStoryService;
    @Autowired
    private ExclusionStoryService exclusionStoryService;

    /** Исключение студента. Удаление из группы */
    @DeleteMapping("/expelStudent")
    public void expelStudent(@RequestBody @Valid ExclusionForm form,
                             BindingResult bindingResult) {
        System.out.println("expelStudent");

        if (bindingResult.hasErrors())
            return;

        restTemplateService.expelStudent(form.getIdStudent());
        exclusionStoryService.save(form);
    }

    @PatchMapping("/transferStudent")
    public void transferStudent(@RequestBody @Valid TransferForm form,
                                BindingResult bindingResult) {
        System.out.println("transferStudent");

        if (bindingResult.hasErrors())
            return;

        restTemplateService.transferStudent(form.getIdStudent(), form.getId_group());
        transferStoryService.save(form);
    }

    @PostMapping("/createAccount")
    public void createStudent(@RequestBody @Valid EnrollmentForm form,
                              BindingResult bindingResult) throws JsonProcessingException {
        System.out.println("createStudent");

        if (bindingResult.hasErrors())
            return;

        restTemplateService.createStudent(form.getAccount());
        Chat chat = new Chat();
        chat.setUsers(Collections.singletonList(form.getAccount().getId()));
        restTemplateService.linkAccountToGroupChat(chat); //TODO привязка студента к чату группы - check

        enrollmentStoryService.save(form);
    }

    @PostMapping("/createGroup")
    public void createGroup(@RequestBody @Valid GroupCreationForm form,
                            BindingResult bindingResult) throws JsonProcessingException {
        System.out.println("createGroup");

        if (bindingResult.hasErrors())
            return;

        form.getGroup().setChat_id(restTemplateService.createChatAndGetId());
        restTemplateService.createGroup(form.getGroup());
        groupCreationFormService.save(form); //maybe errors
    }

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
    public void releaseEvent(@RequestBody @Valid Event event,
                             BindingResult bindingResult) throws JsonProcessingException {
        System.out.println("releaseEvent");

        if (bindingResult.hasErrors())
            return;

        restTemplateService.releaseEvent(event);
    }

}

