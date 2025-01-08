package com.example.auth_service.controllers;

import com.example.auth_service.models.AuthRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/** Интеграционный тест с полной проверкой метода */
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getMyAccountIntegration() throws Exception {
        /*String responseJson = mockMvc.perform(get("/getAuth/getMyAccount")
                        .principal(() -> "Tao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tao"))
                .andReturn()
                .getResponse()
                .getContentAsString();*/

        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("Tao");
        authRequest.setPassword("123456");

        String responseJson = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println("RESPONSE: " + responseJson);
    }
}
