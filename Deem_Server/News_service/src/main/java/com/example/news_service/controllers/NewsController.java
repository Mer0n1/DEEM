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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
                                  @AuthenticationPrincipal PersonDetails personDetails) {
        return newsService.getNewsFeed(personDetails.getFaculty(), date);
    }


    @PostMapping("/createNews")
    public ResponseEntity<?> createNews(@RequestBody @Valid News news,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        News nnews = newsService.createNews(news);

        //Сохранение изображений
        if (news.getImages() != null)
            if (news.getImages().size() != 0) {
                news.getImages().forEach(x -> x.setId_news(nnews.getId()));

                try {
                    restTemplateService.addImagesNews(news.getImages());
                } catch (Exception e) {
                    newsService.deleteNews(nnews);
                    return ResponseEntity.badRequest().body(e.getMessage());
                }
            }

        return ResponseEntity.ok().build();
    }

    public Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors())
            errorMap.put(error.getField(), error.getDefaultMessage());
        return errorMap;
    }
}
