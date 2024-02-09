package com.example.auth_service.controllers;

import com.example.auth_service.dao.AccountDAO;
import com.example.auth_service.dto.PrivateAccountDTO;
import com.example.auth_service.dto.PublicAccountDTO;
import com.example.auth_service.models.Account;
import com.example.auth_service.models.DepartureForm;
import com.example.auth_service.models.Group;
import com.example.auth_service.config.PersonDetails;
import com.example.auth_service.models.ListLong;
import com.example.auth_service.service.AccountService;
import com.example.auth_service.service.AccountServiceClient;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/getAuth")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountServiceClient accountServiceClient;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/getMyAccount")
    public PublicAccountDTO getMyAccount(Principal principal) {
        Account account = accountService.getAccount(principal.getName());
        account.setGroup(accountServiceClient.findGroupById(account.getGroup_id()));
        return convertToPublicAccountDTO(account);
    }

    /** Заметка для дто
     * Скрывает данные: баллы
     */
    @GetMapping("/getAccounts")
    public List<PrivateAccountDTO> getAccounts() {
        List<Account> accounts = accountService.getAccounts();
        return accounts.stream().map(this::convertToPrivateAccountDTO).collect(Collectors.toList());
    }

    @GetMapping("/getTopStudentsFaculty")
    public List<String> getTopStudentsFaculty(Authentication authentication) {
        System.out.println("getTop");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        PersonDetails personDetails = (PersonDetails) userDetails;
        return accountDAO.findTopStudentsFaculty(personDetails.getFaculty());
    }
    @GetMapping("/getTopStudentsUniversity")
    public List<String> getTopStudentsUniversity() {
        return accountService.getTopUniversity();
    }

    @GetMapping("/getListIdUsersGroup")
    public List<Long> getListIdUsersGroup(@RequestParam("id") Long idGroup) {
        return accountService.getUsersOfGroup(idGroup);
    }

    @PreAuthorize("hasRole('HIGH')")
    @PostMapping("/sendScore")
    public void sendScore(@RequestBody @Valid DepartureForm form,
                          BindingResult bindingResult) {
        System.out.println("sendScore");

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PersonDetails personDetails = (PersonDetails) userDetails;

        if (bindingResult.hasErrors())
            return;

        accountService.sendScore(form.getIdAccount(), form.getScore());
    }

    @PreAuthorize("hasRole('HIGH')")
    @PostMapping("/createAccount")
    public Account createAccount(@RequestBody @Valid Account account,
                                        BindingResult bindingResult) {
        System.out.println("createAccount");

        if (bindingResult.hasErrors())
            return null;

        Account ac = accountService.save(account);
        return ac;
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

    @PreAuthorize("hasRole('HIGH')")
    @PostMapping("/getListAverageValues") //неверно, но это меняет ограничения
    public List<Integer> getListAverageValues(@RequestBody List<ListLong> list) {
        System.out.println(list.toString());
        return accountService.getListAverageValue(list);
    }


    private Account convertToAccount(PublicAccountDTO dto) {
        return modelMapper.map(dto, Account.class);
    }
    private PublicAccountDTO convertToPublicAccountDTO(Account account) {
        return modelMapper.map(account, PublicAccountDTO.class);
    }

    private Account convertToAccount(PrivateAccountDTO dto) {
        return modelMapper.map(dto, Account.class);
    }
    private PrivateAccountDTO convertToPrivateAccountDTO(Account account) {
        return modelMapper.map(account, PrivateAccountDTO.class);
    }
}
