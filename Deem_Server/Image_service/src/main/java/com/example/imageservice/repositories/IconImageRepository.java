package com.example.imageservice.repositories;

import com.example.imageservice.models.IconImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IconImageRepository extends JpaRepository<IconImage,Integer> {
    PathImageProjection findByUuid(String uuid);
    IconImage findTopByOrderByIdDesc();
}
