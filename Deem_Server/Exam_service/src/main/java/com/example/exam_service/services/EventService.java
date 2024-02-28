package com.example.exam_service.services;

import com.example.exam_service.models.Event;
import com.example.exam_service.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Transactional
    public void save(Event event) {eventRepository.save(event);}

    @Cacheable("facultyEvents")
    public List<Event> getEvents(String faculty) {
        return eventRepository.getEventsByFaculty(faculty);
    }
    @Cacheable("allEvents")
    public List<Event> getAllEvents() { return eventRepository.findAll(); }
}
