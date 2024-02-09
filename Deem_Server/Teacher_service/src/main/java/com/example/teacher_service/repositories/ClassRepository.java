package com.example.teacher_service.repositories;

import com.example.teacher_service.models.Class;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface ClassRepository extends JpaRepository<Class,Integer> {
    public Optional<Class> findByNameAndDate(String name, Date date);
    public Optional<Class> findByName(String name);
    Optional<Class> findByDate(Date date);
}
