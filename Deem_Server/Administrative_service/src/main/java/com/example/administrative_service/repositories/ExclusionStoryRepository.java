package com.example.administrative_service.repositories;

import com.example.administrative_service.models.ExclusionForm;
import com.example.administrative_service.models.GroupCreationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExclusionStoryRepository extends JpaRepository<ExclusionForm,Integer> {
}
