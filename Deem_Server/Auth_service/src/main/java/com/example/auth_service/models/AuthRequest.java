package com.example.auth_service.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class AuthRequest {

    @NotEmpty(message = "Username must not be null")
    private String username;

    @NotEmpty(message = "Password must not be null")
    private String password;

}
