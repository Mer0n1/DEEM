package com.example.news_service.controllers;

import com.example.news_service.config.PersonDetails;
import com.example.news_service.models.News;
import com.example.news_service.services.NewsService;
import com.example.news_service.services.RestTemplateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.io.Resources;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private RestTemplateService restTemplateService;


   /* @GetMapping("/getAllNews")
    public List<News> getAllNews(Authentication authentication) {
        System.out.println("getNews");

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        PersonDetails personDetails = (PersonDetails) userDetails;

        return newsService.getNews(personDetails.getFaculty());
    }*/

    @GetMapping("/getNewsFeed")
    public List<News> getNewsFeed(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") Date date,
                                  Authentication authentication) {
        System.out.println("getNewsFeed " + date);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        PersonDetails personDetails = (PersonDetails) userDetails;

        return newsService.getNewsFeed(personDetails.getFaculty(), date);
    }


    @PostMapping("/createNews")
    public void createNews(@RequestBody @Valid News news,
                           BindingResult bindingResult) throws JsonProcessingException {
        System.out.println("createNews");

        if (bindingResult.hasErrors())
            return;

        News mnews = newsService.createNews(news);

        //Сохранение изображений
        if (news.getImages() != null)
            if (news.getImages().size() != 0) {
                news.getImages().forEach(x -> x.setId_news(mnews.getId()));
                restTemplateService.addImagesNews(news.getImages());
            }
    }
}
