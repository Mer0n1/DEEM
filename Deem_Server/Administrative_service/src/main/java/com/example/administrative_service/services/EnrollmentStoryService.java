package com.example.administrative_service.services;

import com.example.administrative_service.models.EnrollmentForm;
import com.example.administrative_service.repositories.EnrollmentStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrollmentStoryService {

    @Autowired
    private EnrollmentStoryRepository enrollmentStoryRepository;

    @Transactional
    public void save(EnrollmentForm form) { enrollmentStoryRepository.save(form); }
}
