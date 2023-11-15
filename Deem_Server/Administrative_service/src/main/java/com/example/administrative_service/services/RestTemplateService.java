package com.example.administrative_service.services;

import com.example.administrative_service.models.Account;
import com.example.administrative_service.models.DepartureForm;
import com.example.administrative_service.models.Event;
import com.example.administrative_service.models.Group;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("http://localhost:8082/getAuth")
    private String authServiceUrl;
    @Value("http://localhost:8083/group")
    private String groupServiceUrl;
    @Value("http://localhost:8089/event")
    private String examServiceUrl;

    @Value("${ADMIN_KEY}")
    private String personal_key;

    private MultiValueMap<String, String> headers;
    private HttpEntity<String> entity;

    RestTemplateService() {
        headers = new LinkedMultiValueMap<>();
        personal_key = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJ1c2VybmFtZSI6IlRhbyIsImlkIjo0LCJST0xFIjoiUk9MRV9ISUdIIiwiaWF0IjoxNjk5NjEzODE4LCJpc3MiOiJtZXJvbmkiLCJleHAiOjIwNTk2MTM4MTh9.lEadKCrmESKfqx2-ghLxCeJGuLC20RvB4VJMy_rNMbU";
        headers.add("Authorization", "Bearer " + personal_key);
        headers.set("Content-Type", "application/json");

        entity = new HttpEntity<>("", headers);
    }


    public void sendScore(DepartureForm form) throws JsonProcessingException {
        String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(form);
        entity = new HttpEntity<>(jsonMessage, headers);

        restTemplate.exchange(authServiceUrl + "/sendScore", HttpMethod.POST, entity, Void.class);
    }

    public void createGroup(Group group) throws JsonProcessingException {
        String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(group);
        entity = new HttpEntity<>(jsonMessage, headers);

        restTemplate.exchange(groupServiceUrl + "/createGroup", HttpMethod.POST, entity, Void.class);
    }

    public void createStudent(Account account) throws JsonProcessingException {
        String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(account);
        entity = new HttpEntity<>(jsonMessage, headers);

        restTemplate.exchange(authServiceUrl + "/createAccount", HttpMethod.POST, entity, Void.class);
    }

    public void transferStudent(Long idStudent, Long idGroup) {
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        entity = new HttpEntity<>("idStudent="+idStudent +
                "&idGroup="+idGroup, headers);

        restTemplate.exchange(authServiceUrl + "/transferStudent",
                HttpMethod.POST, entity, Void.class);
    }

    public void expelStudent(Long idStudent) {
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        entity = new HttpEntity<>("idStudent="+idStudent, headers);

        restTemplate.exchange(authServiceUrl + "/deleteAccount",
                HttpMethod.POST, entity, Void.class);
    }

    public void releaseEvent(Event event) throws JsonProcessingException {
        String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(event);
        entity = new HttpEntity<>(jsonMessage, headers);

        restTemplate.exchange(examServiceUrl + "/releaseEvent", HttpMethod.POST,
                entity, Void.class);
    }
}
