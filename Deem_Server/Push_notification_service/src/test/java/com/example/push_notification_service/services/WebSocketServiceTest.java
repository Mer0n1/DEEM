package com.example.push_notification_service.services;

import com.example.push_notification_service.EndPoints.MainContainer;
import com.example.push_notification_service.models.Client;
import com.example.push_notification_service.models.MessagePush;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.tools.javac.Main;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class WebSocketServiceTest {

    private WebSocketService webSocketService;

    @Test
    void sendMessageToClient() throws JsonProcessingException {

    }
}