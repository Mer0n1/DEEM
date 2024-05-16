package com.example.administrative_service.services;

import com.example.administrative_service.models.*;
import com.example.administrative_service.models.Class;
import com.fasterxml.jackson.core.JsonProcessingException;
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

@Service
public class RestTemplateService {

    private final RestTemplate restTemplate;
    private final Environment environment;

    @Value("http://localhost:8082/getAuth")
    private String authServiceUrl;
    @Value("http://localhost:8083/group")
    private String groupServiceUrl;
    @Value("http://localhost:8089/event")
    private String examServiceUrl;
    @Value("http://localhost:8084/chat")
    private String chatControllerUrl;
    @Value("http://localhost:8090/curriculum")
    private String teacherControllerUrl;

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


    public ResponseEntity<?> sendScore(SubmissionForm form) throws JsonProcessingException {
        String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(form);
        entity = new HttpEntity<>(jsonMessage, headers);

        return restTemplate.exchange(authServiceUrl + "/sendScore", HttpMethod.POST, entity, Void.class);
    }

    public ResponseEntity<?> createGroup(Group group) throws JsonProcessingException {
        String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(group);
        entity = new HttpEntity<>(jsonMessage, headers);

        return restTemplate.exchange(groupServiceUrl + "/createGroup", HttpMethod.POST, entity, Void.class);
    }

    public Account createStudent(Account account) throws JsonProcessingException {
        String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(account);
        entity = new HttpEntity<>(jsonMessage, headers);

        return restTemplate.exchange(authServiceUrl + "/createAccount", HttpMethod.POST, entity, Account.class).getBody();
    }

    public ResponseEntity<?> transferStudent(Long idStudent, Long idGroup) {
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        entity = new HttpEntity<>("idStudent="+idStudent +
                "&idGroup="+idGroup, headers);

        return restTemplate.exchange(authServiceUrl + "/transferStudent",
                HttpMethod.POST, entity, Void.class);
    }

    public ResponseEntity<?> expelStudent(Long idStudent) {
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        entity = new HttpEntity<>("idStudent="+idStudent, headers);

        return restTemplate.exchange(authServiceUrl + "/deleteAccount",
                HttpMethod.POST, entity, Void.class);
    }

    public ResponseEntity<?> releaseEvent(Event event) throws JsonProcessingException {
        String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(event);
        entity = new HttpEntity<>(jsonMessage, headers);

        return restTemplate.exchange(examServiceUrl + "/releaseEvent", HttpMethod.POST,
                entity, Void.class);
    }

    public Long createChatAndGetId() throws JsonProcessingException {
        /*String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(chat);
        entity = new HttpEntity<>(jsonMessage, headers);

        restTemplate.exchange(chatControllerUrl + "/createChat", HttpMethod.POST,
                entity, Void.class);*/
        return restTemplate.exchange(chatControllerUrl + "/createChatGroup", HttpMethod.POST,
                entity, Long.class).getBody();
    }

    public void linkAccountToGroupChat(Chat chat) throws JsonProcessingException {
        String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(chat);
        entity = new HttpEntity<>(jsonMessage, headers);

        restTemplate.exchange(chatControllerUrl + "/linkAccountToGroupChat", HttpMethod.POST,
                entity, Void.class);
    }
    public Long getChatId(Long idGroup) {
        try {
            ResponseEntity<Long> response = restTemplate.exchange(
                    groupServiceUrl + "/getChatId" + "?id=" + idGroup, HttpMethod.GET, entity, Long.class);
            return response.getBody();

        } catch(Exception e) {
            return null;
        }
    }

    public ResponseEntity<?> addClass(Class cl) throws JsonProcessingException {

        String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(cl);
        entity = new HttpEntity<>(jsonMessage, headers);

        return restTemplate.exchange(teacherControllerUrl + "/addClass", HttpMethod.POST, entity, Void.class);
    }

    public ResponseEntity<?> deleteClass(Class cl) throws JsonProcessingException {
        String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(cl);
        entity = new HttpEntity<>(jsonMessage, headers);

        return restTemplate.exchange(teacherControllerUrl + "/deleteClass", HttpMethod.POST, entity, Void.class);
    }
}
