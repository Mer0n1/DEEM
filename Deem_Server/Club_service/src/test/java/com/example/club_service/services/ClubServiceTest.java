package com.example.club_service.services;

import com.example.club_service.models.Club;
import com.example.club_service.repositories.ClubRepository;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ClubServiceTest {

    @InjectMocks
    private ClubService clubService;

    @Mock
    private ClubRepository clubRepository;

    @BeforeEach
    void setUp() {
        Club laborClub = new Club();
        laborClub.setId(1L);
        when(clubRepository.findByName("Клуб Труда")).thenReturn(Optional.of(laborClub));

        Club journalismClub = new Club();
        journalismClub.setId(2L);
        when(clubRepository.findByName("Клуб Журналистики")).thenReturn(Optional.of(journalismClub));
    }


    @Test
    public void save() {
        Club club = new Club();

        when(clubRepository.save(club)).thenReturn(club);

        Club club1 = clubService.save(club);

        verify(clubRepository).save(club);
        assertEquals(club, club1);
    }
}
