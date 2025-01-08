package com.example.exam_service.controllers;

import com.example.exam_service.config.PersonDetails;
import com.example.exam_service.models.Event;
import com.example.exam_service.models.EventPush;
import com.example.exam_service.services.EventService;
import com.example.exam_service.services.RestTemplateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EventControllerTest {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    @Mock
    private RestTemplateService restTemplateService;

    @Test
    void getEvents() {
        UserDetails userDetails = new PersonDetails();
        ((PersonDetails) userDetails).setFaculty("TestFaculty");

        when(eventService.getEvents("TestFaculty")).thenReturn(List.of(new Event()));

        List<Event> actualEvents = eventController.getEvents((PersonDetails)userDetails);

        verify(eventService).getEvents("TestFaculty");

        assertNotNull(actualEvents);
        assertEquals(1, actualEvents.size());
    }

    @Test
    void releaseEvent() throws JsonProcessingException {
        EventPush eventPush = new EventPush();
        Event event = new Event();
        eventPush.setEvent(event);
        BindingResult bindingResult = mock(BindingResult.class);

        doNothing().when(eventService).save(eventPush.getEvent());
        when(restTemplateService.pushEventPush(eventPush)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<?> response = eventController.releaseEvent(eventPush, bindingResult);

        verify(eventService).save(eventPush.getEvent());
        verify(restTemplateService).pushEventPush(eventPush);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /*@Test
    void releaseEventException() {
        EventPush eventPush = new EventPush();
        Event event = new Event();
        eventPush.setEvent(event);
        BindingResult bindingResult = mock(BindingResult.class);

        doNothing().when(eventService).save(eventPush.getEvent());
        when(restTemplateService.pushEventPush(eventPush)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<?> response = eventController.releaseEvent(eventPush, bindingResult);

        verify(eventService).save(eventPush.getEvent());
        verify(restTemplateService).pushEventPush(eventPush);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }*/

    @Test
    void getAllEvents() {

        when(eventService.getAllEvents()).thenReturn(List.of(new Event()));

        List<Event> events = eventController.getAllEvents();

        verify(eventService).getAllEvents();

        assertEquals(1, events.size());
    }
}