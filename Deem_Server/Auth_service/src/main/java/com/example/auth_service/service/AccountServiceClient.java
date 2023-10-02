package com.example.auth_service.service;

import com.example.auth_service.models.Group;
import com.example.auth_service.repository.AccountRepository;
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
public class AccountServiceClient {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("http://localhost:8083/group/")
    private String groupServiceUrl;

    @Value("${ADMIN_KEY}")
    private String personal_key;

    private MultiValueMap<String, String> headers;
    private HttpEntity<String> entity;

    AccountServiceClient() {
        headers = new LinkedMultiValueMap<>();
        personal_key = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJ1c2VybmFtZSI6Ik1lcm9uIiwiaWF0IjoxNjk1OTAzOTQzLCJpc3MiOiJtZXJvbmkiLCJleHAiOjE3Mjc0Mzk5NDN9.tROvHVIejKLtlYp5CdN7AMbQJs17THW_u91Vcmcmkzg";
        headers.add("Authorization", "Bearer " + personal_key);

        entity = new HttpEntity<>("body", headers);
    }

    public Group findGroupById(int id) {
        ResponseEntity<Group> response = restTemplate.exchange(
                groupServiceUrl + "getGroup" + "?id=" + id, HttpMethod.GET, entity, Group.class);

        return response.getBody();
    }

    public List<Group> getGroups() {
        ResponseEntity<List<Group>> response = restTemplate.exchange(
                groupServiceUrl + "getGroups", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Group>>(){});

        return response.getBody();
    }
}
