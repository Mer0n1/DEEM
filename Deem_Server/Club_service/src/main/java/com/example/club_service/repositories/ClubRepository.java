package com.example.club_service.repositories;

import com.example.club_service.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club,Integer> {
}
