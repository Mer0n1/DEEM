package com.example.administrative_service.services;


import com.example.administrative_service.models.SubmissionForm;
import com.example.administrative_service.repositories.SubmissionStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SubmissionStoryService {

    @Autowired
    private SubmissionStoryRepository repository;

    @Transactional
    public SubmissionForm save(SubmissionForm form) { return repository.save(form); }

    public List<SubmissionForm> getSubmissionForms(int year, int month) { return repository.findAllByYearAndMonth(year, month); }
}
