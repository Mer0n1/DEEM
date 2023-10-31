package com.example.news_service.services;

import com.example.news_service.models.News;
import com.example.news_service.repositories.NewsRepository;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository repository;

    public List<News> getNews(String faculty) {
        return repository.findAllByFaculty(faculty);
    }

    @Transactional
    public void createNews(News news) {
        repository.save(news);
    }
}
