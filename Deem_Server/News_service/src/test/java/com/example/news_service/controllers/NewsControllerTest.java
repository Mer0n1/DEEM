package com.example.news_service.controllers;

import com.example.news_service.config.PersonDetails;
import com.example.news_service.models.News;
import com.example.news_service.models.images.NewsImage;
import com.example.news_service.services.NewsService;
import com.example.news_service.services.RestTemplateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class NewsControllerTest {

    @InjectMocks
    private NewsController newsController;
    @Mock
    private NewsService newsService;
    @Mock
    private RestTemplateService restTemplateService;

    @Test
    void getNewsFeed() { //TODO
        Date date = new Date();
        PersonDetails personDetails = new PersonDetails();
        personDetails.setFaculty("1");
        UserDetails userDetails = personDetails;
        Authentication authentication = (Authentication) userDetails;

        when(newsService.getNewsFeed(personDetails.getFaculty(), date)).thenReturn(new ArrayList<>());

        List<News> list = newsController.getNewsFeed(date, authentication);

        verify(newsService).getNewsFeed(personDetails.getFaculty(), date);

        assertNotNull(list);
    }

    @Test
    void createNews() throws JsonProcessingException {
        BindingResult bindingResult = mock(BindingResult.class);
        News news = new News();
        news.setImages(List.of(new NewsImage()));

        when(newsService.createNews(news)).thenReturn(news);

        newsController.createNews(news, bindingResult);

        verify(restTemplateService).addImagesNews(news.getImages());
    }
}