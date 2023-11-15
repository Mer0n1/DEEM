package com.example.administrative_service.repositories;

import com.example.administrative_service.models.GroupCreationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Сохранять только после создания группы */
@Repository
public interface GroupCreationRepository extends JpaRepository<GroupCreationForm,Integer> {
}
