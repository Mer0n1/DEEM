package com.example.auth_service.controllers;

import com.example.auth_service.models.Account;
import com.example.auth_service.models.AuthRequest;
import com.example.auth_service.models.LocationStudent;
import com.example.auth_service.service.AccountService;
import com.example.auth_service.service.AccountServiceClient;
import com.example.auth_service.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService service;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountServiceClient accountServiceClient;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void getToken() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("Tao");
        authRequest.setPassword("123456");
        LocationStudent locationStudent = new LocationStudent();

        Account account = new Account();
        account.setGroup_id(1L);

        BindingResult bindingResult = mock(BindingResult.class);
        Authentication authenticate = mock(Authentication.class);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authenticate);
        when(authenticate.isAuthenticated()).thenReturn(true);
        when(accountService.getAccount(authRequest.getUsername())).thenReturn(account);
        when(accountServiceClient.getLocationStudent(1L)).thenReturn(locationStudent);
        when(service.generateToken(account, locationStudent)).thenReturn("0000");

        String token = authController.getToken(authRequest, bindingResult);

        assertEquals("0000", token);
        //verify(authenticationManager).authenticate(authenticate);
        verify(authenticate).isAuthenticated();
        verify(accountService).getAccount(authRequest.getUsername());
        verify(accountServiceClient).getLocationStudent(1L);
        verify(service).generateToken(account, locationStudent);
    }


    @Test
    void getToken_withBindingErrors() {
        AuthRequest authRequest = new AuthRequest();
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(true);
        String response = authController.getToken(authRequest, bindingResult);

        assertNotNull(response);
        verifyNoInteractions(authenticationManager);
    }
}
