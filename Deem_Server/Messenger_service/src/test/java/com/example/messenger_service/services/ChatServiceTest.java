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
import static org.mockito.ArgumentMatchers.*;
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
        Long id = 1L;
        Chat chat = new Chat();
        chat.setId(id);

        when(chatRepository.findChatById(chat.getId())).thenReturn(chat);
        when(chatDAO.getIdAccountsOfChat(chat.getId())).thenReturn(new ArrayList<>());

        Chat TestChat = chatService.getChat(id);

        verify(chatRepository).findChatById(chat.getId());
        verify(chatDAO).getIdAccountsOfChat(chat.getId());

        assertEquals(TestChat, chat);
    }

    @Test
    void save() {
        Chat chat = new Chat();

        when(chatRepository.save(chat)).thenReturn(chat);

        Chat TestChat = chatService.save(chat);

        verify(chatRepository).save(chat);

        assertEquals(TestChat, chat);
    }

    @Test
    void getListChats() {
        String nameAccount = "someName";
        int idAccount = 1;
        Long idChat = 2L;
        List<Chat> chats = List.of(new Chat());
        chats.get(0).setId(idChat);
        chats.get(0).setMessages(new ArrayList<>());
        List<Long> users = List.of(1L, 2L);

        when(chatDAO.getIdAccount(nameAccount)).thenReturn(idAccount);
        when(chatDAO.getChatsIdByAccountId(idAccount)).thenReturn(new ArrayList<>());
        when(chatRepository.findAllByIdIn(anyList())).thenReturn(chats);
        when(chatDAO.getIdAccountsOfChat(idChat)).thenReturn(users);

        List<Chat> list = chatService.getListChats(nameAccount);

        verify(chatDAO).getIdAccount(nameAccount);
        verify(chatDAO).getChatsIdByAccountId(idAccount);
        verify(chatRepository).findAllByIdIn(anyList());
        verify(chatDAO).getIdAccountsOfChat(anyLong());

        assertNotNull(list);
        assertEquals(chats, list);
        assertEquals(list.get(0).getUsers(), users);
    }

    @Test
    void createNewChat() {
        Chat chat = new Chat();
        Message message = new Message();
        chat.setMessages(new ArrayList<>(List.of(message)));

        when(chatRepository.save(chat)).thenReturn(chat);
        when(chatDAO.saveInAccount_chat(chat)).thenReturn(false); //no matter what the value is
        when(messageService.save(any())).thenReturn(new Message());

        chatService.CreateNewChat(chat);

        verify(chatRepository).save(chat);
        verify(messageService).save(message);
        verify(chatDAO).saveInAccount_chat(chat);
    }

}