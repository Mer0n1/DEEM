package com.example.auth_service.service;

import com.example.auth_service.models.Account;
import com.example.auth_service.models.LocationStudent;
import com.example.auth_service.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Transactional
    public String saveUser(Account credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "user added to the system";
    }

    public String generateToken(Account account, LocationStudent locationStudent) {
        return jwtService.generateToken(account, locationStudent);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }


}
