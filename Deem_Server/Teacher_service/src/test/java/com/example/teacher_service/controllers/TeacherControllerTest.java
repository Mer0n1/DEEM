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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TeacherControllerTest {

    @InjectMocks
    private TeacherController teacherController;
    @Mock
    private TeacherService teacherService;


    @Test
    void addTeacher() {
        Teacher teacher = new Teacher();
        BindingResult bindingResult = mock(BindingResult.class);

        doNothing().when(teacherService).save(teacher);

        ResponseEntity<?> response = teacherController.addTeacher(teacher, bindingResult);

        verify(teacherService).save(teacher);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void deleteTeacher() {
        Teacher teacher = new Teacher();
        BindingResult bindingResult = mock(BindingResult.class);

        doNothing().when(teacherService).delete(teacher);

        ResponseEntity<?> response = teacherController.deleteTeacher(teacher, bindingResult);

        verify(teacherService).delete(teacher);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void transferTeacher() {
    }
}