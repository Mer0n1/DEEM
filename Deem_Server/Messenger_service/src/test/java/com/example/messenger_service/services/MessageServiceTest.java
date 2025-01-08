package com.example.messenger_service.services;

import com.example.messenger_service.models.Message;
import com.example.messenger_service.repositories.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService;

    @Test
    void getMessagesFeed_ShouldCallRepositoryWithCorrectParameters() {
        Date date = new Date();
        Long chatId = 1L;
        int feedCount = 15;
        List<Message> messages = List.of(new Message(), new Message());

        ReflectionTestUtils.setField(messageService, "FeedCount", feedCount); //изменяем внешнюю переменную

        Pageable pageable = PageRequest.of(0, feedCount, Sort.by("date").descending());
        when(messageRepository.findAllByDate(date, chatId, pageable)).thenReturn(messages);

        List<Message> result = messageService.getMessagesFeed(date, chatId);

        verify(messageRepository).findAllByDate(date, chatId, pageable);
        assertEquals(messages, result);
    }
}
