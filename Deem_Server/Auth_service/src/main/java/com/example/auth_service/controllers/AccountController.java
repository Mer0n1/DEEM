package com.example.auth_service.controllers;

import com.example.auth_service.dao.AccountDAO;
import com.example.auth_service.dto.PrivateAccountDTO;
import com.example.auth_service.dto.PublicAccountDTO;
import com.example.auth_service.models.*;
import com.example.auth_service.config.PersonDetails;
import com.example.auth_service.service.AccountService;
import com.example.auth_service.service.AccountServiceClient;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/getAccounts")
    public List<PrivateAccountDTO> getAccounts(@AuthenticationPrincipal PersonDetails personDetails) {
        List<Account> accounts = accountService.getAccounts();

        try {
            Account myAccount = accountService.getAccount(personDetails.getId());
            accountService.hideScoreOtherGroup(myAccount.getGroup_id(), accounts);
        } catch (Exception e) {
            return null;
        }

        return accounts.stream().map(this::convertToPrivateAccountDTO).collect(Collectors.toList());
    }

    @GetMapping("/getTopStudentsFaculty")
    public List<String> getTopStudentsFaculty(@AuthenticationPrincipal PersonDetails personDetails) {
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
    @GetMapping("/getAccount")
    public ResponseEntity<?> getAccount(@RequestParam("id") Long id) {
        try {
            Account account = accountService.getAccount(id);

            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('HIGH')")
    @PostMapping("/sendScore")
    public ResponseEntity<?> sendScore(@RequestBody @Valid DepartureForm form,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        try {
            accountService.sendScore(form.getIdAccount(), form.getScore());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('HIGH')")
    @PostMapping("/createAccount")
    public ResponseEntity<?> createAccount(@RequestBody @Valid Account account,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        return ResponseEntity.ok(accountService.save(account));
    }

    @PreAuthorize("hasRole('HIGH')")
    @PostMapping("/transferStudent")
    public ResponseEntity<?> transferStudent(@RequestParam("idStudent") Long idStudent,
                                @RequestParam("idGroup")   Long idGroup) {
        try {
            accountService.transferAccount(idStudent, idGroup);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('HIGH')")
    @PostMapping("/deleteAccount")
    public ResponseEntity<?> deleteAccount(@RequestParam("idStudent") Long idStudent) {
        accountService.delete(idStudent);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('HIGH')")
    @GetMapping ("/getIdGroupAccount")
    public Long getIdGroupAccount(@RequestParam("id") Long idStudent) {
        try {
            return accountService.getAccount(idStudent).getGroup_id();
        } catch (Exception e) {
            return null;
        }
    }

    @PreAuthorize("hasRole('HIGH')")
    @PostMapping("/getListAverageValues") //неверно, но это меняет ограничения
    public List<Integer> getListAverageValues(@RequestBody List<ListLong> list) {
        return accountService.getListAverageValue(list);
    }

    @PreAuthorize("hasRole('HIGH')")
    @PostMapping("/addStudentToClub")
    public ResponseEntity<?> addStudentToClub(@RequestBody @Valid ClubForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        try {
            accountService.addStudentToClub(form);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('HIGH')")
    @PostMapping("/expelStudentClub")
    public ResponseEntity<?> expelStudentClub(@RequestBody @Valid ClubForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        try {
            accountService.expelStudentClub(form);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PreAuthorize("hasRole('HIGH')")
    @PostMapping("/changeRoleClub")
    public ResponseEntity<?> ChangeRoleClub(@RequestBody @Valid ChangeRoleForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        try {
            accountService.changeRoleClub(form);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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

    public Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors())
            errorMap.put(error.getField(), error.getDefaultMessage());
        return errorMap;
    }


}
