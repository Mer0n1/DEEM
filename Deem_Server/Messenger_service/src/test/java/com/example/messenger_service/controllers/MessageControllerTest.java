package com.example.messenger_service.controllers;

import com.example.messenger_service.models.Image.MessageImage;
import com.example.messenger_service.models.Message;
import com.example.messenger_service.services.MessageService;
import com.example.messenger_service.services.MessengerServiceClient;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    @InjectMocks
    private MessageController messageController;

    @Mock
    private MessageService messageService;

    @Mock
    private MessengerServiceClient messengerServiceClient;

    @Test
    void sendMessage() throws JsonProcessingException {
        BindingResult bindingResult = mock(BindingResult.class);
        Message message = new Message();
        message.setImages(List.of(new MessageImage()));

        when(messageService.save(message)).thenReturn(message);

        ResponseEntity<?> responseEntity = messageController.sendMessage(message, bindingResult);

        verify(messageService).save(message);
        verify(messengerServiceClient).addImagesNews(message.getImages());
        verify(messengerServiceClient).pushMessageTo(message);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getMessage() {
        int id = 1;

        when(messageService.getMessage(id)).thenReturn(new Message());

        Message message = messageController.getMessage(id);

        verify(messageService).getMessage(id);
        assertNotNull(message);
    }

    @Test
    void getLastMessages() {

    }
}