package com.example.group_service.services;

import com.example.group_service.models.ListLong;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class RestTemplateClient {

    private final RestTemplate restTemplate;
    private final Environment environment;

    @Value("http://localhost:8082/getAuth/")
    private String accountServiceUrl;

    private String personal_key;

    private MultiValueMap<String, String> headers;
    private HttpEntity<String> entity;

    RestTemplateClient(Environment environment, RestTemplate restTemplate) {
        headers = new LinkedMultiValueMap<>();
        personal_key = environment.getProperty("ADMIN_KEY");
        headers.add("Authorization", "Bearer " + personal_key);

        entity = new HttpEntity<>("body", headers);
        this.environment = environment;
        this.restTemplate = restTemplate;
    }

    public List<Long> getListIdUsersOfGroup(Long idGroup) {
        try {
            headers.set("Content-Type", "text/plain");
            ResponseEntity<List<Long>> response = restTemplate.exchange(
                    accountServiceUrl + "getListIdUsersGroup" + "?id=" + idGroup,
                    HttpMethod.GET, entity, new ParameterizedTypeReference<List<Long>>(){});
            return response.getBody();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<Long> getListTopGroups(List<ListLong> list_id) {
        try {
            String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                    .writeValueAsString(list_id);
            headers.set("Content-Type", "application/json");
            entity = new HttpEntity<>(jsonMessage, headers);

            ResponseEntity<List<Long>> response = restTemplate.exchange(
                    accountServiceUrl + "getListAverageValues",
                    HttpMethod.POST, entity, new ParameterizedTypeReference<List<Long>>(){});
            return response.getBody();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
