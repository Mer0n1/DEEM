package com.example.push_notification_service.controllers;

import com.example.push_notification_service.models.EventPush;
import com.example.push_notification_service.models.MessagePush;
import com.example.push_notification_service.models.News;
import com.example.push_notification_service.services.WebSocketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PushControllerTest {

    @InjectMocks
    private PushController pushController;
    @Mock
    private WebSocketService webSocketService;


    @Test
    void sendMessageToClient() throws JsonProcessingException {
        MessagePush messagePush = new MessagePush();
        BindingResult bindingResult = mock(BindingResult.class);

        doNothing().when(webSocketService).sendMessageToClient(messagePush);

        ResponseEntity<?> responseEntity = pushController.sendMessageToClient(messagePush, bindingResult);

        verify(webSocketService).sendMessageToClient(messagePush);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void sendEventToClient() throws JsonProcessingException {
        EventPush eventPush = new EventPush();
        BindingResult bindingResult = mock(BindingResult.class);

        doNothing().when(webSocketService).sendEventToClient(eventPush);

        ResponseEntity<?> response = pushController.sendEventToClient(eventPush, bindingResult);

        verify(webSocketService).sendEventToClient(eventPush);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void sendNews() throws JsonProcessingException {
        News news = new News();

        doNothing().when(webSocketService).sendNews(news);

        ResponseEntity<?> response = pushController.sendNews(news);

        verify(webSocketService).sendNews(news);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}