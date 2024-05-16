package com.example.administrative_service.controllers;

import com.example.administrative_service.models.*;
import com.example.administrative_service.models.Class;
import com.example.administrative_service.services.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


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
    @Autowired
    private SubmissionStoryService submissionStoryService;


    @PostMapping("/expelStudent") /** Исключение студента. Удаление из группы */
    public ResponseEntity<?> expelStudent(@RequestBody @Valid ExclusionForm form,
                             BindingResult bindingResult) {
        System.out.println("expelStudent");

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        restTemplateService.expelStudent(form.getIdStudent());
        exclusionStoryService.save(form);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transferStudent")
    public ResponseEntity<?> transferStudent(@RequestBody @Valid TransferForm form,
                                BindingResult bindingResult) {
        System.out.println("transferStudent");

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        ResponseEntity<?> responseEntity = restTemplateService.transferStudent(form.getIdStudent(), form.getId_group());
        if (responseEntity.getStatusCode() != HttpStatusCode.valueOf(200))
            return responseEntity;

        form.setDate(new Date(System.currentTimeMillis()));
        transferStoryService.save(form);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/createAccount")
    public ResponseEntity<?> createStudent(@RequestBody @Valid EnrollmentForm form,
                              BindingResult bindingResult) throws JsonProcessingException {
        System.out.println("createStudent");

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        Long id = restTemplateService.createStudent(form.getAccount()).getId();
        form.getAccount().setId(id);

        Chat chat = new Chat();
        chat.setUsers(Collections.singletonList(form.getAccount().getId()));
        chat.setId(restTemplateService.getChatId(form.getAccount().getGroup_id()));
        restTemplateService.linkAccountToGroupChat(chat);

        //enrollmentStoryService.save(form);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/createGroup")
    public ResponseEntity<?> createGroup(@RequestBody @Valid GroupCreationForm form,
                            BindingResult bindingResult) throws JsonProcessingException {
        System.out.println("createGroup");

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        form.getGroup().setDate_create(new Date(System.currentTimeMillis()));
        form.getGroup().setChat_id(restTemplateService.createChatAndGetId());
        return restTemplateService.createGroup(form.getGroup());
        //groupCreationFormService.save(form); //TODO?
    }

    @PostMapping("/sendScore")
    public ResponseEntity<?> sendScore(@RequestBody @Valid SubmissionForm form,
                                          BindingResult bindingResult) throws JsonProcessingException {
        System.out.println("sendScore");

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        ResponseEntity<?> responseEntity = restTemplateService.sendScore(form);
        if (responseEntity.getStatusCode() != HttpStatusCode.valueOf(200))
            return responseEntity;

        form.setDate(new Date(System.currentTimeMillis()));
        submissionStoryService.save(form);

        return ResponseEntity.ok().build();
    }

    /** Выпуск ивента (экзамена) */
    @PostMapping("/releaseEvent")
    public ResponseEntity<?> releaseEvent(@RequestBody @Valid Event event,
                             BindingResult bindingResult) throws JsonProcessingException {
        System.out.println("releaseEvent");

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        return restTemplateService.releaseEvent(event);
    }

    @PostMapping("/addClass")
    public ResponseEntity<?> addClass(@RequestBody @Valid Class cl,
                                         BindingResult bindingResult) throws JsonProcessingException {
        System.out.println("addClass");

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        return restTemplateService.addClass(cl);
    }

    @PostMapping("/deleteClass")
    public ResponseEntity<?> deleteClass(@RequestBody @Valid Class cl,
                                         BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        return restTemplateService.deleteClass(cl);
    }

    public Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors())
            errorMap.put(error.getField(), error.getDefaultMessage());
        return errorMap;
    }
}

