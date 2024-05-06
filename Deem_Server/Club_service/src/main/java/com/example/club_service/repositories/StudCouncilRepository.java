package com.example.club_service.repositories;

import com.example.club_service.models.Club;
import com.example.club_service.models.StudentCouncil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudCouncilRepository extends JpaRepository<StudentCouncil,Integer> {
    Optional<StudentCouncil> findByIdAccount(Long id);
}
