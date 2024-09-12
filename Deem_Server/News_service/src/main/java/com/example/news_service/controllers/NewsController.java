package com.example.news_service.controllers;

import com.example.news_service.config.PersonDetails;
import com.example.news_service.models.CreateNewsDTO;
import com.example.news_service.models.News;
import com.example.news_service.services.NewsService;
import com.example.news_service.services.RestTemplateService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private RestTemplateService restTemplateService;
    @Autowired
    private ModelMapper modelMapper;


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
        return newsService.getNewsFeed(personDetails.getFaculty(), date); //TODO чекнуть на фильтрацию курса внутри факультета
    }


    @PostMapping("/createNews")
    public ResponseEntity<?> createNews(@RequestBody @Valid CreateNewsDTO dto,
                           BindingResult bindingResult) {
        News news = modelMapper.map(dto, News.class);

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

        try {
            restTemplateService.pushNewsTo(news);
        } catch (Exception e) {}

        return ResponseEntity.ok().build();
    }

    public Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors())
            errorMap.put(error.getField(), error.getDefaultMessage());
        return errorMap;
    }
}
