package com.example.club_service.services;

import com.example.club_service.models.Account;
import com.example.club_service.models.ChangeRoleForm;
import com.example.club_service.models.ClubForm;
import com.example.club_service.models.Group;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;


@Service
public class RestTemplateService {

    private final RestTemplate restTemplate;
    private final Environment environment;

    @Value("http://localhost:8082/getAuth")
    private String authServiceUrl;
    @Value("http://localhost:8083/group")
    private String groupServiceUrl;

    private MultiValueMap<String, String> headers;
    private HttpEntity<String> entity;

    RestTemplateService(RestTemplate restTemplate, Environment environment) {
        headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + environment.getProperty("ADMIN_KEY"));
        headers.set("Content-Type", "application/json");

        entity = new HttpEntity<>("", headers);
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    public ResponseEntity<?> createGroup(String shortName) {
        try {
            Group group = new Group();
            group.setDate_create(new Date(System.currentTimeMillis()));
            group.setScore(0);
            group.setFaculty("Общий");
            group.setDescription("");
            group.setCourse(0);
            group.setType("club");
            group.setName(shortName);

            String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                    .writeValueAsString(group);
            entity = new HttpEntity<>(jsonMessage, headers);

            ResponseEntity<Group> response = restTemplate.exchange(
                    groupServiceUrl + "/createGroup", HttpMethod.POST, entity, Group.class);

            return response;

        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> deleteGroup(Long id_group) {
        try {
            headers.set("Content-Type", "application/x-www-form-urlencoded");
            entity = new HttpEntity<>("id_group="+id_group.toString(), headers);

            ResponseEntity<Void> response = restTemplate.exchange(
                    groupServiceUrl + "/deleteGroup", HttpMethod.POST, entity, Void.class);

            return response;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> addStudentToClub(ClubForm form) {

        try {
            String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                    .writeValueAsString(form);
            entity = new HttpEntity<>(jsonMessage, headers);

            ResponseEntity<?> response = restTemplate.exchange(
                    authServiceUrl + "/addStudentToClub", HttpMethod.POST, entity, Void.class);

            return response;
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> expelStudentClub(ClubForm form) {

        try {
            String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                    .writeValueAsString(form);
            entity = new HttpEntity<>(jsonMessage, headers);

            ResponseEntity<?> response = restTemplate.exchange(
                    authServiceUrl + "/expelStudentClub", HttpMethod.POST, entity, Void.class);

            return response;
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> ChangeRole(ChangeRoleForm form) {

        try {
            String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                    .writeValueAsString(form);
            entity = new HttpEntity<>(jsonMessage, headers);

            ResponseEntity<?> response = restTemplate.exchange(
                    authServiceUrl + "/changeRoleClub", HttpMethod.POST, entity, Void.class);

            return response;
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Account getAccount(Long id) throws Exception {

        ResponseEntity<Account> response = restTemplate.exchange(
                authServiceUrl + "/getAccount?id=" + id, HttpMethod.GET, entity, Account.class);
        return response.getBody();
    }

}
