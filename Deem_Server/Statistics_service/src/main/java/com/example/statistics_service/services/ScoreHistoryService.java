package com.example.statistics_service.services;

import com.example.statistics_service.models.StatisticAccount;
import com.example.statistics_service.repositories.ScoreHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScoreHistoryService {

    @Autowired
    private ScoreHistoryRepository scoreHistoryRepository;

    @Transactional
    public void save(StatisticAccount account) { scoreHistoryRepository.save(account); }

    @Transactional
    public void saveAll(List<StatisticAccount> accounts) { scoreHistoryRepository.saveAll(accounts);}

    public StatisticAccount getStatistic(Long idAccount) {
        return scoreHistoryRepository.findByIdAccount(idAccount).orElse(null);
    }

    public List<StatisticAccount> getMonthStatistic(int year, int month) {
        return scoreHistoryRepository.findAllByYearAndMonth(year, month);
    }
}
