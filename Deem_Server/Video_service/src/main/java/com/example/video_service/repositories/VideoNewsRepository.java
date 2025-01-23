package com.example.video_service.repositories;

import com.example.video_service.models.VideoMessage;
import com.example.video_service.models.VideoNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoNewsRepository extends JpaRepository<VideoNews,Integer> {
    @Query("SELECT u.uuid FROM VideoNews u WHERE u.id_news = :id")
    String findUUIDById(@Param("id") Long id);
}
