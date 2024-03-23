package com.example.messenger_service.services;

import com.example.messenger_service.dao.ChatDAO;
import com.example.messenger_service.models.Chat;
import com.example.messenger_service.models.Message;
import com.example.messenger_service.repositories.ChatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @InjectMocks
    private ChatService chatService;
    @Mock
    private ChatRepository chatRepository;
    @Mock
    private ChatDAO chatDAO;
    @Mock
    private MessageService messageService;

    @Test
    void getChats() {

        when(chatRepository.findAllByIdIn(anyList())).thenReturn(new ArrayList<>());

        List<Chat> chatList = chatService.getChats(new ArrayList<>());

        verify(chatRepository).findAllByIdIn(anyList());

        assertNotNull(chatList);
    }

    @Test
    void getChat() {
        Chat chat = new Chat();
        chat.setId(1L);

        when(chatRepository.findChatById(chat.getId())).thenReturn(chat);
        when(chatDAO.getIdAccountsOfChat(chat.getId())).thenReturn(new ArrayList<>());

        Chat TestChat = chatService.getChat(chat.getId());

        verify(chatRepository).findChatById(chat.getId());
        verify(chatDAO).getIdAccountsOfChat(chat.getId());

        assertNotNull(TestChat);
    }

    @Test
    void save() {
        Chat chat = new Chat();

        when(chatRepository.save(chat)).thenReturn(chat);

        Chat TestChat = chatService.save(chat);

        verify(chatRepository).save(chat);

        assertNotNull(TestChat);
    }

    @Test
    void getListChats() { //TODO
        String nameAccount = "someName";

        when(chatDAO.getIdAccount(nameAccount)).thenReturn(1);
        when(chatDAO.getChatsIdByAccountId(1)).thenReturn(List.of(1L));
        when(chatDAO.getIdAccountsOfChat(2L)).thenReturn(new ArrayList<>());

        List<Chat> list = chatService.getListChats(nameAccount);

        verify(chatDAO).getIdAccount(nameAccount);
        verify(chatDAO).getChatsIdByAccountId(1);
        verify(chatDAO).getIdAccountsOfChat(2L);

        assertNotNull(list);
    }

    @Test
    void createNewChat() { //TODO
        Chat chat = new Chat();
        Message message = new Message();
        chat.setMessages(List.of(message));

        chatService.CreateNewChat(chat);

        verify(chatRepository).save(chat);
        verify(messageService).save(message);
        verify(chatDAO.saveInAccount_chat(chat));
    }
}