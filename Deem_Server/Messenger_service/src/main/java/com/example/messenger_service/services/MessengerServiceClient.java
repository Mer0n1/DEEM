package com.example.messenger_service.services;

import com.example.messenger_service.dao.ChatDAO;
import com.example.messenger_service.models.Message;
import com.example.messenger_service.models.MessagePush;
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


@Service
public class MessengerServiceClient {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ChatService chatService;

    @Value("http://localhost:8087/push/sendMessageToClient")
    private String pushServiceUrl;

    @Value("${ADMIN_KEY}")
    private String personal_key;

    private MultiValueMap<String, String> headers;
    private HttpEntity<String> entity;

    MessengerServiceClient() {
        headers = new LinkedMultiValueMap<>();
        personal_key = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJ1c2VybmFtZSI6Ik1lcm9uIiwiaWF0IjoxNjk1OTAzOTQzLCJpc3MiOiJtZXJvbmkiLCJleHAiOjE3Mjc0Mzk5NDN9.tROvHVIejKLtlYp5CdN7AMbQJs17THW_u91Vcmcmkzg";
        headers.add("Authorization", "Bearer " + personal_key);
        headers.set("Content-Type", "application/json");

        entity = new HttpEntity<>("body", headers);
    }

    public void pushMessageTo(Message message) throws JsonProcessingException {
        MessagePush messagePush = new MessagePush();
        messagePush.setMessage(message);
        messagePush.setReceivers(chatService.getChat(message.getChat().getId()).getUsers());
        System.out.println(messagePush.getReceivers() + " " + chatService.getChat(message.getChat().getId()).getUsers());

        String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(messagePush);

        entity = new HttpEntity<>(jsonMessage, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                pushServiceUrl, HttpMethod.POST, entity, Void.class);

    }
}
