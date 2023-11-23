package com.example.exam_service.controllers;

import com.example.exam_service.config.PersonDetails;
import com.example.exam_service.models.Event;
import com.example.exam_service.models.LocationStudent;
import com.example.exam_service.services.EventService;
import com.example.exam_service.services.RestTemplateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private RestTemplateService restTemplateService;

    @GetMapping("/getEvents")
    public List<Event> getEvents(@AuthenticationPrincipal UserDetails userDetails) {

        PersonDetails personDetails = (PersonDetails) userDetails;
        Long idGroup = restTemplateService.getIdGroup(personDetails.getId());
        LocationStudent locationStudent = restTemplateService.getLocationStudent(idGroup);
        return eventService.getEvents(locationStudent.getFaculty());
    }

    @PreAuthorize("hasRole('HIGH')")
    @PostMapping("/releaseEvent")
    public void releaseEvent(@RequestBody @Valid Event event,
                             BindingResult bindingResult) {
        System.out.println("releaseEvent");

        if (bindingResult.hasErrors())
            return;

        eventService.save(event);
    }
}
