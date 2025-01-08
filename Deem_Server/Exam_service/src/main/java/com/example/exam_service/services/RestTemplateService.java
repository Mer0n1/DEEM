package com.example.exam_service.services;

import com.example.exam_service.models.EventPush;
import com.example.exam_service.models.LocationStudent;
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

import java.util.List;

@Service
public class RestTemplateService {
    private final RestTemplate restTemplate;
    private final Environment environment;

    @Value("http://localhost:8082/getAuth")
    private String authServiceUrl;
    @Value("http://localhost:8083/group")
    private String groupServiceUrl;
    @Value("http://localhost:8087/push")
    private String pushServiceUrl;

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


    public LocationStudent getLocationStudent(Long idGroup) {
        try {
            ResponseEntity<LocationStudent> response = restTemplate.exchange(
                    groupServiceUrl + "/getLocationStudent" + "?id=" + idGroup,
                    HttpMethod.GET, entity, LocationStudent.class);
            return response.getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public Long getIdGroup(Long idStudent) {
        try {
            ResponseEntity<Long> response = restTemplate.exchange(
                    authServiceUrl + "/getIdGroupAccount" + "?id=" + idStudent,
                    HttpMethod.GET, entity, Long.class);

            return response.getBody();

        } catch (Exception e) {
            return null;
        }
    }

    public ResponseEntity<?> pushEventPush(EventPush eventPush) throws JsonProcessingException {
        String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(eventPush);

        entity = new HttpEntity<>(jsonMessage, headers);

        return restTemplate.exchange(pushServiceUrl + "/sendEventPush", HttpMethod.POST, entity, ResponseEntity.class);
    }

    /** Проверяем доступность сервиса экзамена */
    //.... test available Exam_Taller_Service...
}
