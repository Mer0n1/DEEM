package com.example.administrative_service.services;

import com.example.administrative_service.models.ExclusionForm;
import com.example.administrative_service.repositories.ExclusionStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExclusionStoryService {

    @Autowired
    private ExclusionStoryRepository exclusionStoryRepository;

    @Transactional
    public void save(ExclusionForm form) { exclusionStoryRepository.save(form); }
}
