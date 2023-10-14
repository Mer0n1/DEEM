package com.example.messenger_service.services;

import com.example.messenger_service.models.Message;
import com.example.messenger_service.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public void save(Message message) {
        messageRepository.save(message);
    }

    public Message getMessage(int id) {
        return messageRepository.findById(id);
    }
}
