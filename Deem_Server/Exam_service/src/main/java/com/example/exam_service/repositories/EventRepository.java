package com.example.exam_service.repositories;

import com.example.exam_service.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Integer> {
    List<Event> getEventsByFaculty(String faculty);
}
