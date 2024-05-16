package com.example.statistics_service.services;

import com.example.statistics_service.models.RecordDateUpdate;
import com.example.statistics_service.repositories.UpdatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdatesHistoryService {

    @Autowired
    private UpdatesRepository repository;

    @Transactional
    public void save(RecordDateUpdate recordDateUpdate) { repository.save(recordDateUpdate); }

    public RecordDateUpdate getLastRecord() { return repository.findFirstByOrderByDateDesc(); }
}
