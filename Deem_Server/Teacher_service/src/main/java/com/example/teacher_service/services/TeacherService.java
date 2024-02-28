package com.example.teacher_service.services;

import com.example.teacher_service.models.Teacher;
import com.example.teacher_service.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository repository;

    @Transactional
    public void save(Teacher teacher) { repository.save(teacher); }

    @Transactional
    public void delete(Teacher teacher) { repository.delete(teacher);}
}
