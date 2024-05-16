package com.example.statistics_service.services;

import com.example.statistics_service.models.Account;
import com.example.statistics_service.models.SubmissionForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
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

    private final RestTemplate restTemplate;
    private final Environment environment;

    @Value("http://localhost:8088/info")
    private String adminServiceUrl;
    @Value("http://localhost:8082/getAuth")
    private String authServiceUrl;

    private String personal_key;

    private MultiValueMap<String, String> headers;
    private HttpEntity<String> entity;

    RestTemplateService(RestTemplate restTemplate, Environment environment) {
        headers = new LinkedMultiValueMap<>();
        personal_key = environment.getProperty("ADMIN_KEY");
        headers.add("Authorization", "Bearer " + personal_key);
        headers.set("Content-Type", "application/json");

        entity = new HttpEntity<>("", headers);
        this.restTemplate = restTemplate;
        this.environment = environment;
    }


    public List<SubmissionForm> getSubmissionForms(int year, int month) {
        return restTemplate.exchange(adminServiceUrl + "/getSubmissionForms?"+ "year=" + year + "&month=" + month,
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<SubmissionForm>>() {}).getBody();
    }

    public List<Account> getAccounts() {
        return restTemplate.exchange(authServiceUrl + "/getAccounts", HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<Account>>() {}).getBody();
    }
}
