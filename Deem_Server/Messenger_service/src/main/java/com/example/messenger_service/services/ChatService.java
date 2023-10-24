package com.example.messenger_service.services;

import com.example.messenger_service.models.Account;
import com.example.messenger_service.models.Chat;
import com.example.messenger_service.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public List<Chat> getChats(List<Long> chatIds) {
        return chatRepository.findAllByIdIn(chatIds);
    }

    public Chat getChat(int id) {
        return chatRepository.findChatById(id);
    }

    @Transactional
    public void save(Chat chat) {
        chatRepository.save(chat);
    }
}
