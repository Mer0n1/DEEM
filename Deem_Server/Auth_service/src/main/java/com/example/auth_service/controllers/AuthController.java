package com.example.auth_service.controllers;

import com.example.auth_service.models.Account;
import com.example.auth_service.models.AuthRequest;
import com.example.auth_service.models.LocationStudent;
import com.example.auth_service.service.AccountService;
import com.example.auth_service.service.AccountServiceClient;
import com.example.auth_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountServiceClient accountServiceClient;

    @Autowired
    private AuthenticationManager authenticationManager;

    /*@PostMapping("/register")
    public String addNewUser(@RequestBody @Valid Account user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return bindingResult.getAllErrors().toString();

        return service.saveUser(user);
    }*/

    @PostMapping("/login")
    public String getToken(@RequestBody @Valid AuthRequest authRequest,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return bindingResult.getAllErrors().toString();

        Authentication authenticate = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authenticate.isAuthenticated()) {
            Account account = accountService.getAccount(authRequest.getUsername());
            LocationStudent locationStudent = //новая зависимость - локация студента
                    accountServiceClient.getLocationStudent(account.getGroup_id());

            if (locationStudent == null)
                return null;
            else
                return service.generateToken(account, locationStudent);

        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }

}
