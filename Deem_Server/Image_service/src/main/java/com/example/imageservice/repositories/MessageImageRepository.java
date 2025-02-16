package com.example.imageservice.repositories;

import com.example.imageservice.models.MessageImage;
import com.example.imageservice.models.NewsImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageImageRepository extends JpaRepository<MessageImage,Integer> {
    @Query("SELECT COUNT(e) FROM MessageImage e WHERE e.id_message = :id")
    int getCount(@Param("id") Long id);

    PathImageProjection findByUuid(String UUID);

    List<PathImageProjection> findAllByUuid(String UUID);
    MessageImage findTopByOrderByIdDesc();
}