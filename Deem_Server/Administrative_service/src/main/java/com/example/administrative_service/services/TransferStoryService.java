package com.example.administrative_service.services;

import com.example.administrative_service.models.TransferForm;
import com.example.administrative_service.repositories.TransferStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferStoryService {
    @Autowired
    private TransferStoryRepository transferStoryRepository;

    @Transactional
    public void save(TransferForm form) { transferStoryRepository.save(form); }
}
