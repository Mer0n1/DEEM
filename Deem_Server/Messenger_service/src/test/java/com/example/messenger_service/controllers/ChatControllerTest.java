package com.example.messenger_service.controllers;

import com.example.messenger_service.dao.ChatDAO;
import com.example.messenger_service.models.Chat;
import com.example.messenger_service.services.ChatService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ChatControllerTest {

    @InjectMocks
    private ChatController controller;

    @Mock
    private ChatService chatService;

    @Mock
    private ChatDAO chatDAO;

    @Test
    void getChats() {
        Principal principal = mock(Principal.class);

        when(principal.getName()).thenReturn("Name");
        when(chatService.getListChats(principal.getName())).thenReturn(new ArrayList<>());

        List<Chat> chats = controller.getChats(principal);

        verify(chatService).getListChats(principal.getName());

        assertNotNull(chats);
    }

    @Test
    void createChat() {
        Chat chat = new Chat();
        BindingResult bindingResult = mock(BindingResult.class);

        controller.createChat(chat, bindingResult);

        verify(chatService).CreateNewChat(chat);
    }

    @Test
    void linkAccountToGroupChat() {
        Chat chat = new Chat();

        controller.linkAccountToGroupChat(chat);

        verify(chatDAO).saveInAccount_chat(chat);
    }

    @Test
    void createChatGroup() {
        Chat chat = new Chat();
        chat.setId(1L);

        when(chatService.save(any())).thenReturn(chat);

        Long l = controller.createChatGroup();

        verify(chatService).save(any());

        assertEquals(chat.getId(), l);
    }
}