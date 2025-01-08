package com.example.exam_service.services;

import com.example.exam_service.models.EventPush;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static reactor.core.publisher.Mono.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RestTemplateServiceTest {

    @InjectMocks
    private RestTemplateService restTemplateService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private HttpEntity<String> entity;

    private String pushServiceUrl = "http://localhost:8087/push";;

    /*@Test
    void pushEventPush() throws JsonProcessingException {
        EventPush eventPush = new EventPush();


        when(restTemplate.exchange(pushServiceUrl + "/sendEventPush", HttpMethod.POST, entity, ResponseEntity.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        restTemplateService.pushEventPush(eventPush);

    }*/

}
