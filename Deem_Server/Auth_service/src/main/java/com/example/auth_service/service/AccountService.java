package com.example.auth_service.service;

import com.example.auth_service.models.Account;
import com.example.auth_service.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;

    public Account getAccount(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    public List<Account> getAccounts() {
        return repository.findAll();
    }
}
