package com.example.administrative_service.repositories;

import com.example.administrative_service.models.SubmissionForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SubmissionStoryRepository extends JpaRepository<SubmissionForm,Integer> {
    //List<SubmissionForm> findByDate(Date date);

    @Query("SELECT e FROM SubmissionForm e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month")
    List<SubmissionForm> findAllByYearAndMonth(@Param("year") int year, @Param("month") int month);
}

