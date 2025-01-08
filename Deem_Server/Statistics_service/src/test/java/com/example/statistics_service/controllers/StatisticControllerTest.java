package com.example.statistics_service.controllers;

import com.example.statistics_service.config.PersonDetails;
import com.example.statistics_service.models.StatisticAccount;
import com.example.statistics_service.services.RestTemplateService;
import com.example.statistics_service.services.ScoreHistoryService;
import com.example.statistics_service.services.UpdatesHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StatisticControllerTest {

    @InjectMocks
    private StatisticController statisticController;
    @Mock
    private RestTemplateService restTemplateService;
    @Mock
    private UpdatesHistoryService updatesHistoryService;
    @Mock
    private ScoreHistoryService scoreHistoryService;

    /*@Test
    void getMyStatistic() {
        PersonDetails personDetails = new PersonDetails();
        personDetails.setId(1L);

        //when(scoreHistoryService.getStatistic(personDetails.getId())).thenReturn(new StatisticAccount());
    }

    @Test
    void getMonthStatistics() {
        int year = 2025;
        int month = 1;

        when(scoreHistoryService.getMonthStatistic(year, month)).thenReturn(new ArrayList<>());

        List<StatisticAccount> list = statisticController.getMonthStatistics(year, month);

        verify(scoreHistoryService).getMonthStatistic(year, month);
        assertNotNull(list);
    }*/
}
