package com.example.exam_service.controllers;

import com.example.exam_service.config.PersonDetails;
import com.example.exam_service.models.Event;
import com.example.exam_service.models.EventPush;
import com.example.exam_service.models.LocationStudent;
import com.example.exam_service.services.EventService;
import com.example.exam_service.services.RestTemplateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private RestTemplateService restTemplateService;

    @GetMapping("/getEvents")
    public List<Event> getEvents(@AuthenticationPrincipal PersonDetails personDetails) {
        return eventService.getEvents(personDetails.getFaculty());
    }

    @PreAuthorize("hasRole('HIGH')")
    @PostMapping("/releaseEvent")
    public ResponseEntity<?> releaseEvent(@RequestBody @Valid EventPush eventPush, //TODO протестировать
                             BindingResult bindingResult) {
        System.out.println("releaseEvent");

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        eventService.save(eventPush.getEvent());
        try {
            restTemplateService.pushEventPush(eventPush); //TODO ?? return restTemplateService.pushEventPush(eventPush);
            return ResponseEntity.ok().build();
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HIGH')")
    @GetMapping("/getAllEvents")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    public Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors())
            errorMap.put(error.getField(), error.getDefaultMessage());
        return errorMap;
    }

}
