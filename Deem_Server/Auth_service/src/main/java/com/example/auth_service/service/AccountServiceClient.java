package com.example.auth_service.service;

import com.example.auth_service.models.Group;
import com.example.auth_service.models.LocationStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.GenericXmlApplicationContext;
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
public class AccountServiceClient {

    private final RestTemplate restTemplate;
    private final Environment environment;

    @Value("http://localhost:8083/group/")
    private String groupServiceUrl;

    private String personal_key;

    private MultiValueMap<String, String> headers;
    private HttpEntity<String> entity;


    AccountServiceClient(RestTemplate restTemplate, Environment environment) {
        headers = new LinkedMultiValueMap<>();
        personal_key = environment.getProperty("ADMIN_KEY");
        headers.add("Authorization", "Bearer " + personal_key);

        entity = new HttpEntity<>("body", headers);
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    public Group findGroupById(Long id) {
        try {
            ResponseEntity<Group> response = restTemplate.exchange(
                    groupServiceUrl + "getGroup" + "?id=" + id, HttpMethod.GET, entity, Group.class);
            return response.getBody();

        } catch(Exception e) {
            return null;
        }
    }

    public List<Group> getGroups() {
        try {
            ResponseEntity<List<Group>> response = restTemplate.exchange(
                    groupServiceUrl + "getGroups", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Group>>() {});
            return response.getBody();

        } catch(Exception e) {
            return null;
        }
    }

    public LocationStudent getLocationStudent(Long idGroup) {
        try {
            ResponseEntity<LocationStudent> response = restTemplate.exchange(
                    groupServiceUrl + "getLocationStudent" + "?id=" + idGroup, HttpMethod.GET, entity, LocationStudent.class);
            return response.getBody();

        } catch(Exception e) {
            return null;
        }
    }
}
