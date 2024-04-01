package com.example.auth_service.controllers;

import com.example.auth_service.config.PersonDetails;
import com.example.auth_service.dao.AccountDAO;
import com.example.auth_service.dto.PrivateAccountDTO;
import com.example.auth_service.dto.PublicAccountDTO;
import com.example.auth_service.models.Account;
import com.example.auth_service.models.DepartureForm;
import com.example.auth_service.models.Group;
import com.example.auth_service.models.ListLong;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    void getMyAccount() {
        String name = "Meron";
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return name;
            }
        };
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
    void getAccounts() {

        Account account = new Account();
        account.setId(1L);

        PrivateAccountDTO privateAccountDTO = new PrivateAccountDTO();
        privateAccountDTO.setId(account.getId());

        List<Account> list = new ArrayList<>();
        list.add(account);

        when(accountService.getAccounts()).thenReturn(list);
        when(modelMapper.map(account, PrivateAccountDTO.class)).thenReturn(privateAccountDTO);

        List<PrivateAccountDTO> privateAccountDTOS = accountController.getAccounts();

        verify(accountService).getAccounts();
        verify(modelMapper).map(account, PrivateAccountDTO.class);

        assertEquals(1, privateAccountDTOS.size());
    }

    @Test
    void getTopStudentsFaculty() { //TODO failed

        // Создание моков
        PersonDetails personDetails = mock(PersonDetails.class);
        AccountDAO accountDAO = mock(AccountDAO.class);

        // Задание поведения моков
        when(personDetails.getFaculty()).thenReturn("EPF");
        when(accountDAO.findTopStudentsFaculty(anyString())).thenReturn(List.of("Student1"));

        List<String> result = accountController.getTopStudentsFaculty(personDetails);

        // Проверка ожидаемого результата
        assertEquals(List.of("Student1"), result);

        // Проверка вызовов методов
        verify(personDetails).getFaculty();
        verify(accountDAO).findTopStudentsFaculty("FacultyName");
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

        when(accountService.getUsersOfGroup(idGroup)).thenReturn(List.of(idGroup));

        List<Long> result = accountController.getListIdUsersGroup(idGroup);

        assertEquals(List.of(idGroup), result);
        verify(accountService).getUsersOfGroup(idGroup);
    }

    @Test
    void sendScore() {
        DepartureForm form = mock(DepartureForm.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(form.getIdAccount()).thenReturn(1L);
        when(form.getScore()).thenReturn(10);

        accountController.sendScore(form, bindingResult);

        verify(accountService).sendScore(1L, 10);
    }

    @Test
    void createAccount() {
        Account account = mock(Account.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(accountService.save(account)).thenReturn(account);

        ResponseEntity<?> TestAccount = accountController.createAccount(account, bindingResult);

        assertEquals(account, TestAccount.getBody());
        verify(accountService).save(account);
    }

    @Test
    void transferStudent() {
        Long idStudent = 1L;
        Long idGroup   = 1L;

        accountController.transferStudent(idStudent, idGroup);

        verify(accountService).transferAccount(idStudent, idGroup);
    }

    @Test
    void deleteAccount() {
        Long idStudent = 1L;

        accountController.deleteAccount(idStudent);

        verify(accountService).delete(idStudent);
    }

    @Test
    void getIdGroupAccount() {
        Long idStudent = 1L;
        Account account = mock(Account.class);

        when(accountService.getAccount(idStudent)).thenReturn(account);
        when(account.getGroup_id()).thenReturn(idStudent);

        Long id = accountController.getIdGroupAccount(idStudent);

        assertEquals(idStudent, id);

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
}