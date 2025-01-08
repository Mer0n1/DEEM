package com.example.club_service.services;

import com.example.club_service.models.Account;
import com.example.club_service.models.StudentCouncil;
import com.example.club_service.repositories.StudCouncilRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StudentCouncilServiceTest {

    @InjectMocks
    private StudentCouncilService studentCouncilService;

    @Mock
    private StudCouncilRepository repository;

    @Test
    void getStudentCouncil() throws Exception {
        Long id_account = 1L;
        Optional<StudentCouncil> obj = Optional.of(new StudentCouncil());

        when(repository.findByIdAccount(id_account)).thenReturn(obj);

        StudentCouncil testObj = studentCouncilService.getStudentCouncil(id_account);

        verify(repository).findByIdAccount(id_account);
        assertNotNull(testObj);
    }

    @Test
    void getStudentCouncilException() {
        Long id_account = 1L;
        Optional<StudentCouncil> obj = Optional.empty();

        when(repository.findByIdAccount(id_account)).thenReturn(obj);

        Exception exception = assertThrows(Exception.class, () -> {
            studentCouncilService.getStudentCouncil(id_account);

        });

        verify(repository).findByIdAccount(id_account);
        assertEquals(exception.getMessage(), "Такой участник не состоит в студсовете");
    }


    @Test
    void addNewMember() {
        Account account = new Account();
        account.setId(1L);

        ArgumentCaptor<StudentCouncil> captor = ArgumentCaptor.forClass(StudentCouncil.class);

        when(repository.save(any())).thenReturn(captor.capture());

        studentCouncilService.addNewMember(account);

        verify(repository).save(captor.capture());

        StudentCouncil captured = captor.getValue();

        assertNotNull(captured);
        assertEquals(1L, captured.getIdAccount());
        assertEquals("member", captured.getType());
    }
}
