package com.example.imageservice.repositories;

import com.example.imageservice.models.DataImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<DataImage,Integer> {
    Optional<DataImage> findByUuid(String uuid);
}
