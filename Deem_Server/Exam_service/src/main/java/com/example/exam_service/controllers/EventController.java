package com.example.exam_service.controllers;

import com.example.exam_service.models.Event;
import com.example.exam_service.services.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/getEvents")
    public List<Event> getEvents() {
        /** через Principle обращаемся в AuthService и получаем faculty */
        return eventService.getEvents("faculty");
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
