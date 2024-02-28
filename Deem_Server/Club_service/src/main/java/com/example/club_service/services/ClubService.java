package com.example.club_service.services;

import com.example.club_service.models.Club;
import com.example.club_service.repositories.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService {

    @Autowired
    private ClubRepository clubRepository;

    @Cacheable("clubs")
    public List<Club> getClubs() { return clubRepository.findAll(); }
}
