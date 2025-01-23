package com.example.video_service.repositories;

import com.example.video_service.models.VideoMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoMessageRepository extends JpaRepository<VideoMessage,Integer> {
    @Query("SELECT u.uuid FROM VideoMessage u WHERE u.id_message = :id")
    String findUUIDById(@Param("id") Long id);
}

