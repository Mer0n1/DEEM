package com.example.auth_service.controllers;

import com.example.auth_service.models.Account;
import com.example.auth_service.models.DepartureForm;
import com.example.auth_service.models.Group;
import com.example.auth_service.security.PersonDetails;
import com.example.auth_service.service.AccountService;
import com.example.auth_service.service.AccountServiceClient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/getAuth")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountServiceClient accountServiceClient;

    @GetMapping("/getMyAccount")
    public Account getMyAccount(Principal principal) {
        Account account = accountService.getAccount(principal.getName());
        account.setGroup(accountServiceClient.findGroupById(account.getGroup_id()));
        return account;
    }

    /** Заметка для дто
     * Скрывает данные: баллы
     */
    @GetMapping("/getAccounts")
    public List<Account> getAccounts() {
        List<Account> accounts = accountService.getAccounts();
        List<Group> groups = accountServiceClient.getGroups();

        for (Account account : accounts)
            for (Group group : groups)
            if (account.getGroup_id() == group.getId()) {
                account.setGroup(group);
                break;
            }

        return accounts;
    }

    @GetMapping("/getTableOfTopUsers")
    public List<Account> getAccountsTops() {
        return accountService.sort(accountService.getAccounts());
    }


    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PostMapping("/sendScore")
    public void sendScore(@RequestBody @Valid DepartureForm form,
                          BindingResult bindingResult) {
        System.out.println("sendScore");

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PersonDetails personDetails = (PersonDetails) userDetails;
        System.out.println(personDetails.getROLE());

        if (bindingResult.hasErrors())
            return;

        accountService.sendScore(form.getIdAccount(), form.getScore());
    }

}
