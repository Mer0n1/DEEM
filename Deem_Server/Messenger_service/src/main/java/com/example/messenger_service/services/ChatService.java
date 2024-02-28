package com.example.messenger_service.services;

import com.example.messenger_service.dao.ChatDAO;
import com.example.messenger_service.models.Account;
import com.example.messenger_service.models.Chat;
import com.example.messenger_service.models.Message;
import com.example.messenger_service.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ChatDAO chatDAO;
    @Autowired
    private MessageService messageService;

    @Cacheable("chats")
    public List<Chat> getChats(List<Long> chatIds) {
        return chatRepository.findAllByIdIn(chatIds);
    }

    @Cacheable("chat")
    public Chat getChat(Long id) {
        Chat chat = chatRepository.findChatById(id);
        chat.setUsers(chatDAO.getIdAccountsOfChat(chat.getId()));

        return chat;
    }

    @Transactional
    public Chat save(Chat chat) {
        return chatRepository.save(chat);
    }

    @Cacheable("chats_account")
    public List<Chat> getListChats(String nameAccount) {
        int accountId = chatDAO.getIdAccount(nameAccount);
        List<Chat> chats = getChats(chatDAO.getChatsIdByAccountId(accountId));

        for (Chat chat : chats) //получим id аккаунтов для каждого чата
            chat.setUsers(chatDAO.getIdAccountsOfChat(chat.getId()));

        return chats;
    }

    @Transactional
    public void CreateNewChat(Chat chat) {

        Message firstMessage = chat.getMessages().get(0);
        chat.getMessages().clear();
        //создание чата в таблице чатов
        Chat updatedChat = save(chat);
        //создание сообщения в таблице сообщений
        firstMessage.setChat(updatedChat);
        messageService.save(firstMessage);
        //создание чата в таблице account_chat
        chatDAO.saveInAccount_chat(chat);
    }



}
