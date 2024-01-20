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
import java.util.Date;


@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ADMIN', 'HIGH')")
public class AdminController {

    @Autowired
    private RestTemplateService restTemplateService;
    @Autowired
    private TransferStoryService transferStoryService;
    @Autowired
    private ExclusionStoryService exclusionStoryService;

    /** Исключение студента. Удаление из группы */
    @PostMapping("/expelStudent")
    public ResponseEntity<Void> expelStudent(@RequestBody @Valid ExclusionForm form,
                             BindingResult bindingResult) {
        System.out.println("expelStudent");

        if (bindingResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        restTemplateService.expelStudent(form.getIdStudent());
        exclusionStoryService.save(form);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/transferStudent")
    public ResponseEntity<Void> transferStudent(@RequestBody @Valid TransferForm form,
                                BindingResult bindingResult) {
        System.out.println("transferStudent");

        if (bindingResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        restTemplateService.transferStudent(form.getIdStudent(), form.getId_group());
        transferStoryService.save(form);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/createAccount")
    public ResponseEntity<Void> createStudent(@RequestBody @Valid EnrollmentForm form,
                              BindingResult bindingResult) throws JsonProcessingException {
        System.out.println("createStudent");

        if (bindingResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Long id = restTemplateService.createStudent(form.getAccount()).getId();
        form.getAccount().setId(id);

        Chat chat = new Chat();
        chat.setUsers(Collections.singletonList(form.getAccount().getId()));
        chat.setId(restTemplateService.getChatId(form.getAccount().getGroup_id()));
        restTemplateService.linkAccountToGroupChat(chat);

        //enrollmentStoryService.save(form);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/createGroup")
    public ResponseEntity<Void> createGroup(@RequestBody @Valid GroupCreationForm form,
                            BindingResult bindingResult) throws JsonProcessingException {
        System.out.println("createGroup");

        if (bindingResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        form.getGroup().setDate_create(new Date(System.currentTimeMillis()));
        form.getGroup().setChat_id(restTemplateService.createChatAndGetId());
        restTemplateService.createGroup(form.getGroup());
        //groupCreationFormService.save(form);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/sendScore")
    public ResponseEntity<Void> sendScore(@RequestBody @Valid DepartureForm form,
                                          BindingResult bindingResult) throws JsonProcessingException {
        System.out.println("sendScore");

        if (bindingResult.hasErrors())
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

        restTemplateService.sendScore(form);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /** Выпуск ивента (экзамена) */
    @PostMapping("/releaseEvent")
    public ResponseEntity<Void> releaseEvent(@RequestBody @Valid Event event,
                             BindingResult bindingResult) throws JsonProcessingException {
        System.out.println("releaseEvent");
        System.out.println(event.toString());

        if (bindingResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        restTemplateService.releaseEvent(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

