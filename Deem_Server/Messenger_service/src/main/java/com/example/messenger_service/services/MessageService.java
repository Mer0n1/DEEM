package com.example.messenger_service.services;

import com.example.messenger_service.models.Message;
import com.example.messenger_service.repositories.MessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    @Autowired
    private MessengerServiceClient messengerServiceClient;

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


    /** Собранное сообщение.
     * Получение видео uuid и самого сообщения*/
    public List<Message> getCollectedMessagesFeed(Date date, Long chatId) {
        return getCollectedMessages(getMessagesFeed(date, chatId));
    }

    public List<Message> getCollectedMessages(List<Message> messageList) {
        //Собираем uuid видео если оно есть
        for (Message message : messageList)
            if (message.getThereVideo())
                message.setVideoUUID(messengerServiceClient.getVideoUUID(message.getId()).getBody());
        return messageList;
    }

    public void doImage(Message message, Message actualObject) throws JsonProcessingException {
        if (message.getImages() != null)
            if (message.getImages().size() != 0) {
                message.getImages().forEach(x -> x.setId_message(actualObject.getId()));
                messengerServiceClient.addImagesNews(message.getImages());
            }
    }

    public void pushMessage(Message message, List<Long> users) throws JsonProcessingException {
        messengerServiceClient.pushMessageTo(message, users); //отправляем запрос на уведомления
    }

}
