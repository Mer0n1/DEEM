package com.example.messenger_service.services;

import com.example.messenger_service.models.Message;
import com.example.messenger_service.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public void delete(Message message) { messageRepository.delete(message);}

    @Cacheable("messages")
    public Message getMessage(int id) {
        return messageRepository.findById(id);
    }
}
