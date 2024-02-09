package com.example.teacher_service.repositories;

import com.example.teacher_service.models.Class;
import com.example.teacher_service.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher,Integer> {

}
