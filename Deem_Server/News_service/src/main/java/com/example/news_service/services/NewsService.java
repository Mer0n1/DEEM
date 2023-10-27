package com.example.news_service.services;

import com.example.news_service.models.News;
import com.example.news_service.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

    @Autowired
    private NewsRepository repository;

    public News getNews() {
        return repository.findAll().get(0);
    }
}
