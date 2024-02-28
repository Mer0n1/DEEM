package com.example.news_service.repositories;

import com.example.news_service.models.News;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    List<News> findAllByFaculty(String faculty);

    @Query("SELECT n FROM News n WHERE n.faculty = ?1 AND n.date < ?2")
    List<News> findAllByFacultyAndDate(String faculty, Date date, Pageable pageable);
}