package com.example.club_service.services;

import com.example.club_service.models.Club;
import com.example.club_service.repositories.ClubRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClubService {

    private final ClubRepository clubRepository;

    public static Long id_labor_club;
    public static Long id_journalism_club;


    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;

        id_labor_club = this.clubRepository.findByName("Клуб Труда").get().getId(); //TODO
        id_journalism_club = this.clubRepository.findByName("Клуб Журналистики").get().getId();
    }

    @Transactional
    public Club save(Club club) { return clubRepository.save(club); }

    @Transactional
    public void deleteClub(Club club) { clubRepository.delete(club);}

    @Cacheable("clubs")
    public List<Club> getClubs() { return clubRepository.findAll(); }

    @Cacheable("club")
    public Club getClub(String name) { return clubRepository.findByName(name).orElse(null); }

    public Club createClub(String name, Long id_group, Long id_leader) {
        Club club = new Club();
        club.setName(name);
        club.setId_group(id_group);
        club.setId_leader(id_leader);

        return save(club);
    }
}
