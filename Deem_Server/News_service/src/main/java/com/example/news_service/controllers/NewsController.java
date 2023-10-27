package com.example.news_service.controllers;

import com.example.news_service.models.News;
import com.example.news_service.services.NewsService;
import com.google.common.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;


    //from
    @GetMapping("/test")
    public News getNews() throws IOException {
        System.out.println("getNews");
        News news = newsService.getNews();

        File file = new File("");
        byte[] fileBytes = Files.readAllBytes(file.toPath());

        String encodedString = Base64.getEncoder().encodeToString(fileBytes);
        news.setImg(encodedString);

        //byte[] decodedBytes = Base64.decode(encodedString, Base64.DEFAULT);
        //Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

        return news;
    }
}
