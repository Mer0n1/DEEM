package com.example.auth_service.service;

import com.example.auth_service.models.Account;
import com.example.auth_service.models.ChangeRoleForm;
import com.example.auth_service.models.ClubForm;
import com.example.auth_service.models.ListLong;
import com.example.auth_service.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository repository;

    @Test
    void save() {
        Account account = new Account();

        when(repository.save(account)).thenReturn(account);

        Account TestAccount = accountService.save(account);

        assertEquals(account, TestAccount);
        verify(repository).save(account);
    }

    @Test
    void delete() {
        Long idAccount = 1L;

        doNothing().when(repository).deleteById(anyLong());

        accountService.delete(idAccount);

        verify(repository).deleteById(idAccount);
    }

    @Test
    void getAccount() throws Exception {

        String username = "Student";
        Long idStudent = 1L;
        Optional<Account> optionalAccount = Optional.of(new Account());

        when(repository.findByUsername(username)).thenReturn(optionalAccount);
        when(repository.findById(idStudent)).thenReturn(optionalAccount);

        Account account  = accountService.getAccount(username);
        Account account1 = accountService.getAccount(idStudent);

        assertEquals(optionalAccount.get(), account);
        assertEquals(optionalAccount.get(), account1);
        verify(repository).findByUsername(username);
        verify(repository).findById(idStudent);
    }

    @Test
    void getAccountException() {
        Long idStudent = 1L;;

        when(repository.findById(idStudent)).thenReturn(Optional.empty());

        //Проверяем, что метод выбрасывает исключение
        Exception exception = assertThrows(Exception.class, () -> {
            accountService.getAccount(idStudent);
        });

        assertEquals(exception.getMessage(), new Exception("Такого аккаунта не существует").getMessage());
        verify(repository).findById(idStudent);
    }


    @Test
    void getAccounts() {
        List<Account> accountList = new ArrayList<>();

        when(repository.findAll()).thenReturn(accountList);

        List<Account> TestAccountList = accountService.getAccounts();

        assertEquals(accountList, TestAccountList);
        verify(repository).findAll();
    }

    @Test
    void getUsersOfGroup() {
        Long idGroup = 1L;

        when(repository.findUsersOfGroup(idGroup)).thenReturn(List.of(1L));

        List<Long> TestList = accountService.getUsersOfGroup(idGroup);

        assertEquals(List.of(1L), TestList);
        verify(repository).findUsersOfGroup(idGroup);
    }

    @Test
    void getTopUniversity() {
        List<Account> accounts = new ArrayList<>();
        Account account = new Account();
        account.setName("Name");
        account.setSurname("Surname");
        accounts.add(account);

        when(repository.findTopUniversity()).thenReturn(accounts);

        List<String> list = accountService.getTopUniversity();

        assertEquals(account.getName() + " " + account.getSurname(), list.get(0));
        verify(repository).findTopUniversity();
    }

    @Test
    void sendScore() throws Exception {

        Long idAccount = 1L;
        int addScore = 1;
        int beginScore = 10;

        Account account = new Account();
        account.setScore(beginScore);
        Optional<Account> accountOpt = Optional.of(account);

        when(repository.findById(idAccount)).thenReturn(accountOpt);

        accountService.sendScore(idAccount, addScore);

        assertEquals(11, account.getScore());
        verify(repository).findById(idAccount);
    }

    @Test
    void sendScoreException() {
        Long idAccount = 1L;
        int addScore = 1;

        Optional<Account> accountOpt = Optional.empty();

        when(repository.findById(idAccount)).thenReturn(accountOpt);

        Exception exception = assertThrows(Exception.class, () -> {
            accountService.sendScore(idAccount, addScore);
        });

        assertEquals(exception.getMessage(), "Пользователь не найден");
        verify(repository).findById(idAccount);
    }


    @Test
    void transferAccountStandard() throws Exception {

        Long idStudent = 1L;
        Long idGroup = 1L;

        Account account = new Account();
        account.setId(idStudent);
        Optional<Account> accountOpt = Optional.of(account);

        when(repository.findById(idStudent)).thenReturn(accountOpt);

        accountService.transferAccount(idStudent, idGroup);

        assertEquals(idGroup, account.getGroup_id());
        verify(repository).findById(idStudent);
    }

    @Test
    void transferAccountAlreadyInGroup() {
        Long idStudent = 1L;
        Long idGroup = 1L;

        Account account = new Account();
        account.setGroup_id(idGroup);
        account.setId(idStudent);
        Optional<Account> accountOpt = Optional.of(account);

        when(repository.findById(idStudent)).thenReturn(accountOpt);

        Exception exception = assertThrows(Exception.class, () -> {
            accountService.transferAccount(idStudent, idGroup);
        });

        assertEquals(exception.getMessage(), "Студент уже состоит в этой группе");
        verify(repository).findById(idStudent);
    }

    @Test
    void transferAccountNotFound() {
        Long idStudent = 1L;
        Long idGroup = 1L;

        Optional<Account> accountOpt = Optional.empty();

        when(repository.findById(idStudent)).thenReturn(accountOpt);

        Exception exception = assertThrows(Exception.class, () -> {
            accountService.transferAccount(idStudent, idGroup);
        });

        assertEquals(exception.getMessage(), "Пользователь не найден");
        verify(repository).findById(idStudent);
    }


    @Test
    void addStudentToClub() throws Exception {
        ClubForm form = new ClubForm();
        Account account = new Account();

        form.setId_club(1L);
        form.setId_student(10L);
        account.setId(form.getId_student());
        Optional<Account> optionalAccount = Optional.of(account);

        when(repository.findById(form.getId_student())).thenReturn(optionalAccount);

        accountService.addStudentToClub(form);

        assertEquals(account.getId_club(), form.getId_club());
        verify(repository).findById(form.getId_student());
        verify(repository).save(account);
    }

    @Test
    void addStudentToClubNotFound() {
        ClubForm form = new ClubForm();
        form.setId_student(1L);
        Optional<Account> optionalAccount = Optional.empty();

        when(repository.findById(form.getId_student())).thenReturn(optionalAccount);

        Exception exception = assertThrows(Exception.class, () -> {
            accountService.addStudentToClub(form);
        });

        assertEquals(exception.getMessage(), "Такого аккаунта не существует");
        verify(repository).findById(form.getId_student());
    }

    @Test
    void addStudentToClubAlreadyInClub()  {
        ClubForm form = new ClubForm();
        Account account = new Account();

        form.setId_club(1L);
        form.setId_student(10L);
        account.setId(form.getId_student());
        account.setId_club(form.getId_club());
        Optional<Account> optionalAccount = Optional.of(account);

        when(repository.findById(form.getId_student())).thenReturn(optionalAccount);

        Exception exception = assertThrows(Exception.class, () -> {
            accountService.addStudentToClub(form);
        });

        assertEquals(exception.getMessage(), "Студент уже состоит в клубе");
        verify(repository).findById(form.getId_student());
    }

    @Test
    void expelStudentClub() throws Exception {
        ClubForm form = new ClubForm();
        Account account = new Account();

        form.setId_club(1L);
        form.setId_student(10L);
        account.setId(form.getId_student());
        account.setId_club(form.getId_club());
        Optional<Account> optionalAccount = Optional.of(account);

        when(repository.findById(form.getId_student())).thenReturn(optionalAccount);

        accountService.expelStudentClub(form);

        assertNull(account.getId_club());
        verify(repository).findById(form.getId_student());
        verify(repository).save(account);
    }

    @Test
    void expelStudentClubNotFound() {
        ClubForm form = new ClubForm();
        form.setId_club(1L);
        form.setId_student(10L);
        Optional<Account> optionalAccount = Optional.empty();

        when(repository.findById(form.getId_student())).thenReturn(optionalAccount);

        Exception exception = assertThrows(Exception.class, () -> {
            accountService.expelStudentClub(form);
        });

        assertEquals(exception.getMessage(), "Такого аккаунта не существует");
        verify(repository).findById(form.getId_student());
    }

    @Test
    void expelStudentClubNotInClub() {
        ClubForm form = new ClubForm();
        Account account = new Account();
        form.setId_club(1L);
        form.setId_student(10L);
        account.setId(form.getId_student());
        account.setId_club(2L); //в другом клубе
        Optional<Account> optionalAccount = Optional.of(account);

        when(repository.findById(form.getId_student())).thenReturn(optionalAccount);

        Exception exception = assertThrows(Exception.class, () -> {
            accountService.expelStudentClub(form);
        });

        assertEquals(exception.getMessage(), "Студент не состоит в клубе");
        verify(repository).findById(form.getId_student());
    }

    @Test
    void changeRoleClub() throws Exception {
        ChangeRoleForm form = new ChangeRoleForm();
        form.setNewRole("ADMIN");

        Account account = new Account();
        Optional<Account> optionalAccount = Optional.of(account);

        when(repository.findById(form.getId_student())).thenReturn(optionalAccount);

        accountService.changeRole(form);

        assertEquals(account.getRole(), "ROLE_" + form.getNewRole());
    }

    @Test
    void changeRoleClubNotFound() {
        ChangeRoleForm form = new ChangeRoleForm();
        Optional<Account> optionalAccount = Optional.empty();

        when(repository.findById(form.getId_student())).thenReturn(optionalAccount);

        Exception exception = assertThrows(Exception.class, () -> {
            accountService.changeRole(form);
        });

        assertEquals(exception.getMessage(), "Такого аккаунта не существует");
    }

    @Test
    void getListAverageValue() {
        ListLong listLong1 = new ListLong();
        ListLong listLong2 = new ListLong();
        listLong1.list = List.of(1L, 2L, 3L);
        listLong2.list = List.of(4L, 5L);

        List<ListLong> inputList = List.of(listLong1, listLong2);

        // Создаем тестовые аккаунты с баллами
        Account account1 = new Account();
        account1.setScore(90);
        Account account2 = new Account();
        account2.setScore(70);
        Account account3 = new Account();
        account3.setScore(80);

        Account account4 = new Account();
        account4.setScore(100);
        Account account5 = new Account();
        account5.setScore(50);

        when(repository.findAllByIdIn(listLong1.list)).thenReturn(List.of(account1, account2, account3));
        when(repository.findAllByIdIn(listLong2.list)).thenReturn(List.of(account4, account5));

        List<Integer> result = accountService.getListAverageValue(inputList);

        assertEquals(2, result.size());
        assertEquals(80, result.get(0));
        assertEquals(75, result.get(1));

        verify(repository).findAllByIdIn(listLong1.list);
        verify(repository).findAllByIdIn(listLong2.list);
    }
}
