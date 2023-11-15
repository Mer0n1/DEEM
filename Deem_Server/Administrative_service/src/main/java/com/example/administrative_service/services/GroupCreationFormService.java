package com.example.administrative_service.services;

import com.example.administrative_service.models.GroupCreationForm;
import com.example.administrative_service.repositories.GroupCreationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupCreationFormService {
    @Autowired
    private GroupCreationRepository groupCreationRepository;

    @Transactional
    public void save(GroupCreationForm form) { groupCreationRepository.save(form);}
}
