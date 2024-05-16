package com.example.statistics_service.repositories;


import com.example.statistics_service.models.RecordDateUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdatesRepository extends JpaRepository<RecordDateUpdate,Integer> {
    RecordDateUpdate findFirstByOrderByDateDesc();
}
