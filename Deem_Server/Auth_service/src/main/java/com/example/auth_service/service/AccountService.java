package com.example.auth_service.service;

import com.example.auth_service.models.Account;
import com.example.auth_service.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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


    public List<Account> sort(List<Account> accounts) {
        List<Account> tops = new ArrayList<>();
        accounts.stream().sorted(Comparator.comparing(o -> o.getScore()));

        int size = Math.min(accounts.size(), 10);
        for (int j = 0; j < size; j++)
            tops.add(accounts.get(j));
        return tops;
    }
}
