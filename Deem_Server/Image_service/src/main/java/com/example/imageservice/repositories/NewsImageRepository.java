package com.example.imageservice.repositories;

import com.example.imageservice.models.NewsImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsImageRepository extends JpaRepository<NewsImage,Integer> {
    @Query("SELECT COUNT(e) FROM NewsImage e WHERE e.id_news = :id")
    int getCount(@Param("id") Long id);

    PathImageProjection findByUuid(String UUID);

    NewsImage findTopByOrderByIdDesc();
}
