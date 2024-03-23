package com.example.teacher_service.controllers;

import com.example.teacher_service.models.Class;
import com.example.teacher_service.models.Teacher;
import com.example.teacher_service.services.TeacherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TeacherControllerTest {

    @InjectMocks
    private TeacherController teacherController;
    @Mock
    private TeacherService teacherService;


    @Test
    void addTeacher() {
        Teacher cl = new Teacher();
        BindingResult bindingResult = mock(BindingResult.class);

        teacherController.addTeacher(cl, bindingResult);

        verify(teacherService).save(cl);
    }

    @Test
    void deleteTeacher() {
        Teacher cl = new Teacher();
        BindingResult bindingResult = mock(BindingResult.class);

        teacherController.deleteTeacher(cl, bindingResult);

        verify(teacherService).delete(cl);
    }

    @Test
    void transferTeacher() {
    }
}