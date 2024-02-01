package com.example.news_service.services;

import com.example.news_service.models.News;
import com.example.news_service.repositories.NewsRepository;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository repository;

    @Cacheable("news")
    public List<News> getNews(String faculty) {
        return repository.findAllByFaculty(faculty);
    }


    @Transactional
    public News createNews(News news) {return repository.save(news);}
}
