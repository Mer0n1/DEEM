package com.example.news_service.controllers;

import com.example.news_service.config.PersonDetails;
import com.example.news_service.models.CreateNewsDTO;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Mock
    private ModelMapper modelMapper;

    @Test
    void getNewsFeed() {
        Date date = new Date();
        PersonDetails personDetails = new PersonDetails();
        personDetails.setFaculty("EPF");
        personDetails.setCourse(1);

        when(newsService.getNewsFeed(personDetails.getFaculty(), date, personDetails.getCourse())).thenReturn(new ArrayList<>());

        List<News> list = newsController.getNewsFeed(date, personDetails);

        verify(newsService).getNewsFeed(personDetails.getFaculty(), date, personDetails.getCourse());
        assertNotNull(list);
    }

    @Test
    void createNews_andSaveImage() throws Exception {
        BindingResult bindingResult = mock(BindingResult.class);
        CreateNewsDTO dto = new CreateNewsDTO();
        PersonDetails personDetails = new PersonDetails();
        News news = new News();
        news.setImages(new ArrayList<>(List.of(new NewsImage())));
        news.setId(1L);
        personDetails.setFaculty("EPF");
        personDetails.setCourse(1);

        when(modelMapper.map(dto, News.class)).thenReturn(news);
        when(newsService.createNews(news)).thenReturn(news);
        when(restTemplateService.addImagesNews(news.getImages())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        doNothing().when(restTemplateService).pushNewsTo(news);

        ResponseEntity<?> response = newsController.createNews(dto, bindingResult, personDetails);

        verify(modelMapper).map(dto, News.class);
        verify(newsService).createNews(news);
        verify(restTemplateService).addImagesNews(news.getImages());
        verify(restTemplateService).pushNewsTo(news);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void createNews_andException() throws Exception {
        BindingResult bindingResult = mock(BindingResult.class);
        CreateNewsDTO dto = new CreateNewsDTO();
        PersonDetails personDetails = new PersonDetails();
        News news = new News();
        news.setImages(new ArrayList<>(List.of(new NewsImage())));
        news.setId(1L);
        personDetails.setFaculty("EPF");
        personDetails.setCourse(1);

        when(modelMapper.map(dto, News.class)).thenReturn(news);
        when(newsService.createNews(news)).thenReturn(news);
        when(restTemplateService.addImagesNews(news.getImages())).thenThrow(new RuntimeException());
        doNothing().when(newsService).deleteNews(news);

        ResponseEntity<?> response = newsController.createNews(dto, bindingResult, personDetails);

        verify(modelMapper).map(dto, News.class);
        verify(newsService).createNews(news);
        verify(restTemplateService).addImagesNews(news.getImages());
        verify(newsService).deleteNews(news);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}