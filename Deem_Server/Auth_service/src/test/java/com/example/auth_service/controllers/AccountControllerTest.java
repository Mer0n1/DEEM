package com.example.auth_service.controllers;

import com.example.auth_service.config.PersonDetails;
import com.example.auth_service.dao.AccountDAO;
import com.example.auth_service.dto.PrivateAccountDTO;
import com.example.auth_service.dto.PublicAccountDTO;
import com.example.auth_service.models.*;
import com.example.auth_service.service.AccountService;
import com.example.auth_service.service.AccountServiceClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountServiceClient accountServiceClient;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    AccountDAO accountDAO;

    @Test
    void getMyAccount() {
        Principal principal = () -> "Name";

        Group group = new Group();
        group.setId(1L);

        Account account = new Account();
        account.setName("Meron");
        account.setGroup_id(1L);

        PublicAccountDTO publicAccountDTONew = new PublicAccountDTO();
        publicAccountDTONew.setId(account.getId());
        publicAccountDTONew.setGroup(group);
        publicAccountDTONew.setGroup_id(group.getId());

        when(accountService.getAccount(principal.getName())).thenReturn(account);
        when(accountServiceClient.findGroupById(account.getGroup_id())).thenReturn(group);
        when(modelMapper.map(account, PublicAccountDTO.class)).thenReturn(publicAccountDTONew);

        PublicAccountDTO publicAccountDTO = accountController.getMyAccount(principal);

        verify(accountService).getAccount(principal.getName());
        verify(accountServiceClient).findGroupById(1L);
        verify(modelMapper).map(account, PublicAccountDTO.class);

        assertEquals(publicAccountDTONew, publicAccountDTO);
    }

   @Test
    void getAccounts() throws Exception {

        PersonDetails personDetails = new PersonDetails();
        personDetails.setId(1L);

        Account account = new Account();
        account.setId(1L);

        PrivateAccountDTO privateAccountDTO = new PrivateAccountDTO();
        privateAccountDTO.setId(account.getId());

        List<Account> list = new ArrayList<>();
        list.add(account);

        when(accountService.getAccounts()).thenReturn(list);
        when(accountService.getAccount(personDetails.getId())).thenReturn(account);
        when(modelMapper.map(account, PrivateAccountDTO.class)).thenReturn(privateAccountDTO);

        List<PrivateAccountDTO> privateAccountDTOS = accountController.getAccounts(personDetails);

        verify(accountService).getAccounts();
        verify(accountService).hideScoreOtherGroup(account.getGroup_id(), list);
        verify(modelMapper).map(account, PrivateAccountDTO.class);

        assertEquals(1, privateAccountDTOS.size());
        assertEquals(privateAccountDTO.getId(), privateAccountDTOS.get(0).getId());

   }

   @Test
    public void getAccountsException() throws Exception {
        PersonDetails personDetails = new PersonDetails();
        personDetails.setId(1L);

        when(accountService.getAccount(personDetails.getId())).thenThrow(new Exception("Такого аккаунта не существует"));

        List<PrivateAccountDTO> accounts = accountController.getAccounts(personDetails);

        assertNull(accounts);
       //verify(accountService.getAccount(personDetails.getId()));
   }

    @Test
    void getTopStudentsFaculty() {
        PersonDetails personDetails = new PersonDetails();
        personDetails.setFaculty("EPF");

        when(accountDAO.findTopStudentsFaculty(anyString())).thenReturn(List.of("Student1"));

        List<String> result = accountController.getTopStudentsFaculty(personDetails);

        assertEquals(List.of("Student1"), result);
        verify(accountDAO).findTopStudentsFaculty("EPF");
    }

    @Test
    void getTopStudentsUniversity() {

        when(accountService.getTopUniversity()).thenReturn(List.of("Student1"));

        List<String> result = accountController.getTopStudentsUniversity();

        assertEquals(List.of("Student1"), result);
        verify(accountService).getTopUniversity();
    }

    @Test
    void getListIdUsersGroup() {
        Long idGroup = 1L;

        when(accountService.getUsersOfGroup(idGroup)).thenReturn(List.of(1L,2L,3L));

        List<Long> result = accountController.getListIdUsersGroup(idGroup);

        assertNotNull(result);
        verify(accountService).getUsersOfGroup(idGroup);
    }

    @Test
    void getAccount() throws Exception {
        Long id = 1L;
        Account account = new Account();

        when(accountService.getAccount(id)).thenReturn(account);

        ResponseEntity<?> result = accountController.getAccount(id);

        assertNotNull(result.getBody());
        assertEquals(result.getBody(), account);
        assertEquals(result.getStatusCode(), HttpStatus.OK);

        verify(accountService).getAccount(id);
    }

    @Test
    void getAccountException() throws Exception {
        Long id = 1L;

        when(accountService.getAccount(anyLong())).thenThrow(new Exception("Error"));

        ResponseEntity<?> responseEntity = accountController.getAccount(id);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(responseEntity.getBody(), "Error");
    }


    @Test
    void sendScore() throws Exception {

        DepartureForm form = new DepartureForm();
        form.setIdAccount(1L);
        form.setScore(100);

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<?> response = accountController.sendScore(form, bindingResult);

        verify(accountService).sendScore(form.getIdAccount(), form.getScore());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void sendScoreExceptions() throws Exception {

        DepartureForm form = new DepartureForm();
        form.setIdAccount(1L);
        form.setScore(100);

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        doThrow(new Exception("Error")).when(accountService).sendScore(form.getIdAccount(), form.getScore());

        ResponseEntity<?> response = accountController.sendScore(form, bindingResult);

        verify(accountService).sendScore(form.getIdAccount(), form.getScore());
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void createAccount() {
        Account account = new Account();
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(accountService.save(account)).thenReturn(account);

        ResponseEntity<?> result = accountController.createAccount(account, bindingResult);

        assertEquals(account, result.getBody());
        verify(accountService).save(account);
    }

    @Test
    void transferStudent() throws Exception {

        Long idStudent = 1L;
        Long idGroup   = 1L;

        doNothing().when(accountService).transferAccount(idStudent, idGroup);

        ResponseEntity<?> responseEntity = accountController.transferStudent(idStudent, idGroup);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(accountService).transferAccount(idStudent, idGroup);
    }

    @Test
    void transferStudentException() throws Exception {

        Long idStudent = 1L;
        Long idGroup   = 1L;

        doThrow(new Exception("Error")).when(accountService).transferAccount(idStudent, idGroup);
        doNothing().when(accountService).transferAccount(idStudent, idGroup);

        ResponseEntity<?> responseEntity = accountController.transferStudent(idStudent, idGroup);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        verify(accountService).transferAccount(idStudent, idGroup);
    }



    @Test
    void deleteAccount() {
        Long idStudent = 1L;

        doNothing().when(accountService).delete(idStudent);

        ResponseEntity<?> responseEntity = accountController.deleteAccount(idStudent);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertNull(responseEntity.getBody());
        verify(accountService).delete(idStudent);
    }

    @Test
    void getIdGroupAccount() throws Exception {
        Long idStudent = 1L;
        Account account = new Account();
        account.setGroup_id(2L);

        when(accountService.getAccount(idStudent)).thenReturn(account);

        Long id = accountController.getIdGroupAccount(idStudent);

        assertEquals(account.getGroup_id(), id);
        verify(accountService).getAccount(idStudent);
    }

    @Test
    void getListAverageValues() {
        List<ListLong> list = new ArrayList<>();

        when(accountService.getListAverageValue(list)).thenReturn(List.of(1));

        List<Integer> TestList = accountController.getListAverageValues(list);

        assertEquals(List.of(1), TestList);
        verify(accountService).getListAverageValue(list);
    }

    @Test
    void addStudentToClub() throws Exception {
        ClubForm form = new ClubForm();
        BindingResult bindingResult = mock(BindingResult.class);

        doNothing().when(accountService).addStudentToClub(form);

        ResponseEntity<?> response = accountController.addStudentToClub(form, bindingResult);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        verify(accountService).addStudentToClub(form);
    }

    @Test
    void addStudentToClubException() throws Exception {
        ClubForm form = new ClubForm();
        BindingResult bindingResult = mock(BindingResult.class);

        doThrow(new Exception("Error")).when(accountService).addStudentToClub(form);

        ResponseEntity<?> response = accountController.addStudentToClub(form, bindingResult);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        verify(accountService).addStudentToClub(form);
    }

    @Test
    void expelStudentClub() throws Exception {
        ClubForm form = new ClubForm();
        BindingResult bindingResult = mock(BindingResult.class);

        doNothing().when(accountService).expelStudentClub(form);

        ResponseEntity<?> response = accountController.expelStudentClub(form, bindingResult);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        verify(accountService).expelStudentClub(form);
    }

    @Test
    void expelStudentClubException() throws Exception {
        ClubForm form = new ClubForm();
        BindingResult bindingResult = mock(BindingResult.class);

        doThrow(new Exception("Error")).when(accountService).expelStudentClub(form);

        ResponseEntity<?> response = accountController.expelStudentClub(form, bindingResult);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        verify(accountService).expelStudentClub(form);
    }

    @Test
    void ChangeRoleClub() throws Exception {
        ChangeRoleForm form = new ChangeRoleForm();
        BindingResult bindingResult = mock(BindingResult.class);

        doNothing().when(accountService).changeRole(form);

        ResponseEntity<?> response = accountController.ChangeRole(form, bindingResult);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        verify(accountService).changeRole(form);
    }

    @Test
    void ChangeRoleClubException() throws Exception {
        ChangeRoleForm form = new ChangeRoleForm();
        BindingResult bindingResult = mock(BindingResult.class);

        doThrow(new Exception("Error")).when(accountService).changeRole(form);

        ResponseEntity<?> response = accountController.ChangeRole(form, bindingResult);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        verify(accountService).changeRole(form);
    }
}
