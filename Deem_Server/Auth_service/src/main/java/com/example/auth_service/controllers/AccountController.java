package com.example.auth_service.controllers;

import com.example.auth_service.models.Account;
import com.example.auth_service.models.DepartureForm;
import com.example.auth_service.models.Group;
import com.example.auth_service.config.PersonDetails;
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


    @PreAuthorize("hasRole('HIGH')")
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

    @PreAuthorize("hasRole('HIGH')")
    @PostMapping("/createAccount")
    public void createAccount(@RequestBody @Valid Account account,
                          BindingResult bindingResult) {
        System.out.println("createAccount");
        if (bindingResult.hasErrors())
            return;

        accountService.save(account);
    }

    @PreAuthorize("hasRole('HIGH')")
    @PostMapping("/transferStudent")
    public void transferStudent(@RequestParam("idStudent") Long idStudent,
                                @RequestParam("idGroup")   Long idGroup) {
        System.out.println("transferStudent " + idStudent + " " + idGroup);

        accountService.transferAccount(idStudent, idGroup);
    }

    @PreAuthorize("hasRole('HIGH')")
    @PostMapping("/deleteAccount")
    public void deleteAccount(@RequestParam("idStudent") Long idStudent) {
        System.out.println("deleteAccount");

        accountService.delete(idStudent);
    }

    @PreAuthorize("hasRole('HIGH')")
    @GetMapping ("/getIdGroupAccount")
    public Long getIdGroupAccount(@RequestParam("id") Long idStudent) {
        return accountService.getAccount(idStudent).getGroup_id();
    }

    @GetMapping("/getListIdUsersGroup")
    public List<Long> getListIdUsersGroup(@RequestParam("id") Long idGroup) {
        return accountService.getUsersOfGroup(idGroup);
    }

}
