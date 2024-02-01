package com.example.group_service.repositories;

import com.example.group_service.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group,Integer> {
    Optional<Group> findById(Long id);
    List<Group> findAllByFacultyAndCourse(String faculty, Integer course);

}
