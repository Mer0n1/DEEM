package com.example.push_notification_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestTemplateService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("http://localhost:8082/getAuth/getNicknamesOfAccountsById")
    private String authServiceUrl;

    @Value("${ADMIN_KEY}")
    private String personal_key;

    private MultiValueMap<String, String> headers;
    private HttpEntity<String> entity;

    RestTemplateService() {
        headers = new LinkedMultiValueMap<>();
        personal_key = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJ1c2VybmFtZSI6Ik1lcm9uIiwiaWF0IjoxNjk1OTAzOTQzLCJpc3MiOiJtZXJvbmkiLCJleHAiOjE3Mjc0Mzk5NDN9.tROvHVIejKLtlYp5CdN7AMbQJs17THW_u91Vcmcmkzg";
        headers.add("Authorization", "Bearer " + personal_key);

        entity = new HttpEntity<>("body", headers);
    }

    public String getAccountUsername(Long id) {
        ResponseEntity<String> response = restTemplate.exchange(
                authServiceUrl + "?id=" + id, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }



}
