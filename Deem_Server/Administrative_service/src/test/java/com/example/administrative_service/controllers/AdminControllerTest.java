package com.example.administrative_service.controllers;

import com.example.administrative_service.models.*;
import com.example.administrative_service.services.ExclusionStoryService;
import com.example.administrative_service.services.RestTemplateService;
import com.example.administrative_service.services.SubmissionStoryService;
import com.example.administrative_service.services.TransferStoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @Mock
    private SubmissionStoryService submissionStoryService;

    @Test
    void expelStudent() {
        ExclusionForm form = new ExclusionForm();
        form.setIdStudent(1L);
        BindingResult bindingResult = mock(BindingResult.class);

        when(restTemplateService.expelStudent(form.getIdStudent())).thenReturn(null);
        doNothing().when(exclusionStoryService).save(form);

        ResponseEntity<?> responseEntity = adminController.expelStudent(form, bindingResult);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(restTemplateService).expelStudent(form.getIdStudent());
        verify(exclusionStoryService).save(form);
    }

    @Test
    void transferStudent() {
        TransferForm form = new TransferForm();
        form.setIdStudent(1L);
        form.setId_group(2L);
        BindingResult bindingResult = mock(BindingResult.class);

        when(restTemplateService.transferStudent(form.getIdStudent(), form.getId_group())).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<?> responseEntity = adminController.transferStudent(form, bindingResult);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(form.getDate());
        verify(restTemplateService).transferStudent(form.getIdStudent(), form.getId_group());
        verify(transferStoryService).save(form);
    }

    @Test
    void transferStudentBadRequest() {
        TransferForm form = new TransferForm();
        form.setIdStudent(1L);
        form.setId_group(2L);
        BindingResult bindingResult = mock(BindingResult.class);

        when(restTemplateService.transferStudent(form.getIdStudent(),
                form.getId_group())).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        ResponseEntity<?> responseEntity = adminController.transferStudent(form, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        verify(restTemplateService).transferStudent(form.getIdStudent(), form.getId_group());
    }

    @Test
    void createStudent() throws JsonProcessingException {
        BindingResult bindingResult = mock(BindingResult.class);
        Account account = new Account();
        account.setGroup_id(2L);
        EnrollmentForm form = new EnrollmentForm();
        form.setAccount(account);

        Account account1 = new Account();
        account1.setId(1L);
        Long chatId = 3L;

        when(restTemplateService.createStudent(account)).thenReturn(account1);
        when(restTemplateService.getChatId(account.getGroup_id())).thenReturn(chatId);
        doNothing().when(restTemplateService).linkAccountToGroupChat(any());

        ResponseEntity<?> responseEntity = adminController.createStudent(form, bindingResult);

        verify(restTemplateService).createStudent(account);
        verify(restTemplateService).getChatId(any());
        verify(restTemplateService).linkAccountToGroupChat(any());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void createGroup() throws JsonProcessingException {
        BindingResult bindingResult = mock(BindingResult.class);
        GroupCreationForm form = new GroupCreationForm();
        form.setGroup(new Group());
        Long chatId = 1L;

        when(restTemplateService.createChatAndGetId()).thenReturn(chatId);
        when(restTemplateService.createGroup(form.getGroup())).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<?> responseEntity = adminController.createGroup(form, bindingResult);

        verify(restTemplateService).createChatAndGetId();
        verify(restTemplateService).createGroup(form.getGroup());

        assertNotNull(responseEntity);
        assertNotNull(form.getGroup().getDate_create());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(form.getGroup().getChat_id(), chatId);
    }

    @Test
    void sendScore() throws JsonProcessingException {
        BindingResult bindingResult = mock(BindingResult.class);
        SubmissionForm form = new SubmissionForm();

        when(restTemplateService.sendScore(form)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<?> responseEntity = adminController.sendScore(form, bindingResult);

        verify(restTemplateService).sendScore(form);
        verify(submissionStoryService).save(form);

        assertNotNull(responseEntity);
        assertNotNull(form.getDate());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void sendScoreBadRequest() throws JsonProcessingException {
        BindingResult bindingResult = mock(BindingResult.class);
        SubmissionForm form = new SubmissionForm();

        when(restTemplateService.sendScore(form)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        ResponseEntity<?> responseEntity = adminController.sendScore(form, bindingResult);

        verify(restTemplateService).sendScore(form);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void releaseEvent() throws JsonProcessingException {
        EventPush form = new EventPush();
        BindingResult bindingResult = mock(BindingResult.class);

        when(restTemplateService.releaseEvent(form)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<?> responseEntity = adminController.releaseEvent(form, bindingResult);

        verify(restTemplateService).releaseEvent(form);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void addClass() throws JsonProcessingException {
        Class cl = new Class();
        BindingResult bindingResult = mock(BindingResult.class);

        when(restTemplateService.addClass(cl)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<?> responseEntity = adminController.addClass(cl, bindingResult);

        verify(restTemplateService).addClass(cl);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteClass() throws JsonProcessingException {
        Class cl = new Class();
        BindingResult bindingResult = mock(BindingResult.class);

        when(restTemplateService.deleteClass(cl)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<?> responseEntity = adminController.deleteClass(cl, bindingResult);

        verify(restTemplateService).deleteClass(cl);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}