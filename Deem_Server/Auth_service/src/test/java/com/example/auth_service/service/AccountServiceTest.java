package com.example.auth_service.service;

import com.example.auth_service.models.Account;
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

/*
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository repository;

    @Test
    void save() {
        Account account = mock(Account.class);

        when(repository.save(account)).thenReturn(account);

        Account TestAccount = accountService.save(account);

        assertEquals(account, TestAccount);
        verify(repository).save(account);
    }

    @Test
    void delete() {
        Long idAccount = 1L;

        accountService.delete(idAccount);

        verify(repository).deleteById(idAccount);
    }

    @Test
    void getAccount() {
        */
/*String username = "Student";
        Long idStudent = 1L;
        Optional<Account> optionalAccount = Optional.of(new Account());

        when(repository.findByUsername(username)).thenReturn(optionalAccount);
        when(repository.findById(idStudent)).thenReturn(optionalAccount);

        Account account  = accountService.getAccount(username);
        Account account1 = accountService.getAccount(idStudent);

        assertEquals(optionalAccount.get(), account);
        assertEquals(optionalAccount.get(), account1);
        verify(repository).findByUsername(username);
        verify(repository).findById(idStudent);*//*

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
        accounts.add(new Account());
        accounts.get(0).setUsername("Student");

        when(repository.findTopUniversity()).thenReturn(accounts);

        List<String> list = accountService.getTopUniversity();

        assertEquals("Student", list.get(0));
        verify(repository).findTopUniversity();
    }

    @Test
    void sendScore() {
        */
/*Long idAccount = 1L;
        int score = 1;
        Account account = new Account();
        account.setScore(9);
        Optional<Account> accountOpt = Optional.of(account);

        when(repository.findById(idAccount)).thenReturn(accountOpt);

        accountService.sendScore(idAccount, score);

        assertEquals(10, account.getScore());
        verify(repository).findById(idAccount);*//*

    }

    @Test
    void transferAccount() {
        */
/*Long idStudent = 1L;
        Long idGroup = 1L;
        Optional<Account> accountOpt = Optional.of(new Account());

        when(repository.findById(idStudent)).thenReturn(accountOpt);

        accountService.transferAccount(idStudent, idGroup);

        assertEquals(idGroup, accountOpt.get().getGroup_id());
        verify(repository).findById(idStudent);*//*

    }

    @Test
    void getListAverageValue() {
        List<ListLong> list = new ArrayList<>();
        list.add(new ListLong());

        Account account = new Account();
        account.setScore(10);
        Account account1 = new Account();
        account1.setScore(20);
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);
        accountList.add(account1);

        when(repository.findAllByIdIn(list.get(0).list)).thenReturn(accountList);

        List<Integer> listInteger = accountService.getListAverageValue(list);

        assertEquals(15, listInteger.get(0));
        verify(repository).findAllByIdIn(list.get(0).list);
    }
}*/
