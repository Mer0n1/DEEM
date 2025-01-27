package com.example.messenger_service.services;

import com.example.messenger_service.dao.ChatDAO;
import com.example.messenger_service.models.Image.MessageImage;
import com.example.messenger_service.models.Message;
import com.example.messenger_service.models.MessagePush;
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
public class MessengerServiceClient {

    private final RestTemplate restTemplate;
    private final Environment environment;

    @Value("http://localhost:8087/push/sendMessageToClient")
    private String pushServiceUrl;
    @Value("http://localhost:8086/image")
    private String imageServiceUrl;
    @Value("http://localhost:8093/video")
    private String videoServiceUrl;

    private String personal_key;

    private MultiValueMap<String, String> headers;
    private HttpEntity<String> entity;

    MessengerServiceClient(RestTemplate restTemplate, Environment environment/*, ChatService chatService*/) {
        headers = new LinkedMultiValueMap<>();
        personal_key = environment.getProperty("ADMIN_KEY");
        headers.add("Authorization", "Bearer " + personal_key);
        headers.set("Content-Type", "application/json");
        entity = new HttpEntity<>("body", headers);

        this.restTemplate = restTemplate;
        this.environment = environment;
    }
 
    public void pushMessageTo(Message message, List<Long> users) throws JsonProcessingException {
        MessagePush messagePush = new MessagePush();
        messagePush.setMessage(message);
        messagePush.setReceivers(users);

        String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(messagePush);

        entity = new HttpEntity<>(jsonMessage, headers);

        try {
            restTemplate.exchange(pushServiceUrl, HttpMethod.POST, entity, Void.class);
        } catch(Exception e) {}
    }

    public ResponseEntity<?> addImagesNews(List<MessageImage> imgs) throws JsonProcessingException {

        String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(imgs);
        entity = new HttpEntity<>(jsonMessage, headers);

        return restTemplate.exchange(imageServiceUrl + "/addImagesMessage", HttpMethod.POST, entity, Void.class);
    }

    public ResponseEntity<String> getVideoUUID(Long idMessage) {
        return restTemplate.exchange(videoServiceUrl + "/getUUID" + "?type=message_video&id="
                + idMessage, HttpMethod.GET, entity, String.class);
    }
}
