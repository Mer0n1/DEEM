package com.example.teacher_service.controllers;

import com.example.teacher_service.models.Class;
import com.example.teacher_service.services.ClassService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CurriculumControllerTest {

    @InjectMocks
    private CurriculumController curriculumController;
    @Mock
    private ClassService classService;


    @Test
    void getTwoWeek() {

        when(classService.getClasses()).thenReturn(new ArrayList<>());

        List<Class> classList = curriculumController.getTwoWeek();

        verify(classService).getClasses();

        assertNotNull(classList);
    }

    @Test
    void addClass() {
        Class cl = new Class();
        BindingResult bindingResult = mock(BindingResult.class);

        when(classService.save(any())).thenReturn(cl);

        ResponseEntity<?> response = curriculumController.addClass(cl, bindingResult);

        verify(classService).save(cl);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void deleteClass() {
        Class cl = new Class();
        BindingResult bindingResult = mock(BindingResult.class);

        doNothing().when(classService).delete(cl);

        ResponseEntity<?> response = curriculumController.deleteClass(cl, bindingResult);

        verify(classService).delete(cl);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}