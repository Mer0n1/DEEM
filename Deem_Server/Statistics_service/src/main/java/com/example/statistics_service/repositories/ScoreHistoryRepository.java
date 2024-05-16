package com.example.statistics_service.repositories;

import com.example.statistics_service.models.RecordDateUpdate;
import com.example.statistics_service.models.StatisticAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScoreHistoryRepository extends JpaRepository<StatisticAccount,Integer> {
    Optional<StatisticAccount> findByIdAccount(Long id);

    @Query("SELECT e FROM StatisticAccount e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month")
    List<StatisticAccount> findAllByYearAndMonth(@Param("year") int year, @Param("month") int month);
}
