package com.example.news_service.services;

import com.example.news_service.models.News;
import com.example.news_service.repositories.NewsRepository;
import jakarta.persistence.Transient;
import org.hibernate.query.spi.Limit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository repository;

    @Value("${FeedCount}")
    private int FeedCount;

    @Cacheable("news")
    public List<News> getNews(String faculty) {
        return repository.findAllByFaculty(faculty);
    }

    @Cacheable("newsFeed")
    public List<News> getNewsFeed(String faculty, Date date) {
        Pageable pageable = PageRequest.of(0, FeedCount, Sort.by("date").descending());
        return repository.findAllByFacultyAndDate(faculty, date, pageable);
    }

    @Transactional
    public News createNews(News news) {
        return repository.save(news);
    }

    @CachePut(value = "news", key = "#news.getId()")
    public News updateCache(News news) {
        return news;
    }


}
