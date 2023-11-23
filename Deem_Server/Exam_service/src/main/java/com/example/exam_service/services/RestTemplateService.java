package com.example.exam_service.services;

import com.example.exam_service.models.LocationStudent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("http://localhost:8082/getAuth")
    private String authServiceUrl;
    @Value("http://localhost:8083/group")
    private String groupServiceUrl;

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


    public LocationStudent getLocationStudent(Long idGroup) {
        ResponseEntity<LocationStudent> response = restTemplate.exchange(
                groupServiceUrl + "/getLocationStudent" + "?id="+idGroup,
                HttpMethod.GET, entity, LocationStudent.class);
        return response.getBody();
    }

    public Long getIdGroup(Long idStudent) {
        ResponseEntity<Long> response = restTemplate.exchange(
                authServiceUrl + "/getIdGroupAccount" + "?id="+idStudent,
                HttpMethod.GET, entity, Long.class);
        return response.getBody();
    }
}
