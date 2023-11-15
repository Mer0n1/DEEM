package com.example.administrative_service.repositories;

import com.example.administrative_service.models.EnrollmentForm;
import com.example.administrative_service.models.TransferForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferStoryRepository extends JpaRepository<TransferForm,Integer> {
}
