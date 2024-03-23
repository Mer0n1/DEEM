package com.example.administrative_service.controllers;

import com.example.administrative_service.models.*;
import com.example.administrative_service.services.ExclusionStoryService;
import com.example.administrative_service.services.RestTemplateService;
import com.example.administrative_service.services.TransferStoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import com.example.administrative_service.models.Class;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private RestTemplateService restTemplateService;

    @Mock
    private TransferStoryService transferStoryService;

    @Mock
    private ExclusionStoryService exclusionStoryService;

    @Test
    void expelStudent() {
        ExclusionForm form = mock(ExclusionForm.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(form.getIdStudent()).thenReturn(1L);

        ResponseEntity<?> responseEntity = adminController.expelStudent(form, bindingResult);

        verify(bindingResult).hasErrors();
        verify(restTemplateService).expelStudent(1L);
        verify(exclusionStoryService).save(form);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void transferStudent() {
        TransferForm form = mock(TransferForm.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(form.getIdStudent()).thenReturn(1L);
        when(form.getId_group()).thenReturn(1L);

        ResponseEntity<?> responseEntity = adminController.transferStudent(form, bindingResult);

        verify(bindingResult).hasErrors();
        verify(restTemplateService).transferStudent(1L, 1L);
        verify(transferStoryService).save(form);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void createStudent() throws JsonProcessingException {
        EnrollmentForm form = mock(EnrollmentForm.class);
        BindingResult bindingResult = mock(BindingResult.class);
        Account account = new Account();
        account.setId(1L);
        account.setGroup_id(2L);

        when(form.getAccount()).thenReturn(account);
        when(restTemplateService.createStudent(account)).thenReturn(account);
        when(restTemplateService.getChatId(account.getGroup_id())).thenReturn(3L);

        ResponseEntity<?> responseEntity = adminController.createStudent(form, bindingResult);

        verify(bindingResult).hasErrors();
        verify(restTemplateService).createStudent(account);
        verify(restTemplateService).getChatId(any());
        verify(restTemplateService).linkAccountToGroupChat(any());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void createGroup() throws JsonProcessingException {
        GroupCreationForm form = mock(GroupCreationForm.class);
        BindingResult bindingResult = mock(BindingResult.class);
        Group group = new Group();

        when(form.getGroup()).thenReturn(group);
        when(restTemplateService.createChatAndGetId()).thenReturn(1L);

        ResponseEntity<?> responseEntity = adminController.createGroup(form, bindingResult);

        verify(restTemplateService).createChatAndGetId();
        verify(restTemplateService).createGroup(group);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void sendScore() throws JsonProcessingException {
        DepartureForm form = mock(DepartureForm.class);
        BindingResult bindingResult = mock(BindingResult.class);

        ResponseEntity<?> responseEntity = adminController.sendScore(form, bindingResult);

        verify(restTemplateService).sendScore(form);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void releaseEvent() throws JsonProcessingException {
        Event form = mock(Event.class);
        BindingResult bindingResult = mock(BindingResult.class);

        ResponseEntity<?> responseEntity = adminController.releaseEvent(form, bindingResult);

        verify(restTemplateService).releaseEvent(form);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void addClass() {
        Class cl = mock(Class.class);
        BindingResult bindingResult = mock(BindingResult.class);

        ResponseEntity<?> responseEntity = adminController.addClass(cl, bindingResult);

        verify(restTemplateService).addClass(cl);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteClass() {
        Class cl = mock(Class.class);
        BindingResult bindingResult = mock(BindingResult.class);

        ResponseEntity<?> responseEntity = adminController.deleteClass(cl, bindingResult);

        verify(restTemplateService).deleteClass(cl);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}