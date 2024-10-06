package com.example.messenger_service.services;

import com.example.messenger_service.models.Message;
import com.example.messenger_service.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Value("${FeedCount}")
    private int FeedCount;

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public void delete(Message message) { messageRepository.delete(message);}

    @Cacheable("messages")
    public Message getMessage(int id) {
        return messageRepository.findById(id);
    }

    public List<Message> getMessagesFeed(Date date, Long chatId) {
        Pageable pageable = PageRequest.of(0, FeedCount, Sort.by("date").descending());
        return messageRepository.findAllByDate(date, chatId, pageable);
    }
}
