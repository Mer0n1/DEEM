package com.example.news_service.services;

import com.example.news_service.models.News;
import com.example.news_service.repositories.NewsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.Transient;
import org.hibernate.query.spi.Limit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository repository;
    @Autowired
    private RestTemplateService restTemplateService;

    @Value("${FeedCount}")
    private int FeedCount;

    @Cacheable("news")
    public List<News> getNews(String faculty) {
        return repository.findAllByFaculty(faculty);
    }

    @Cacheable("newsFeed")
    public List<News> getNewsFeed(String faculty, Date date, Integer course) {
        Pageable pageable = PageRequest.of(0, FeedCount, Sort.by("date").descending());
        return repository.findAllByFacultyAndDate(faculty, date, course, pageable); 
    }

    public List<News> getCollectedNews(String faculty, Date date, Integer course) {
        return getCollectedNews(getNewsFeed(faculty, date, course));
    }

    public List<News> getCollectedNews(List<News> newsList) {
        //Собираем uuid видео если оно есть
        for (News news : newsList)
            if (news.getThereVideo())
                try {
                    news.setVideoUUID(restTemplateService.getVideoUUID(news.getId()).getBody());
                } catch (Exception e) {} //в случае если сервис недоступен
        return newsList;
    }

    public void pushNewsTo(News news) throws Exception {
        restTemplateService.pushNewsTo(news);
    }

    public void doImage(News news, News actualNews) throws JsonProcessingException {
        //Сохранение изображений
        if (news.getImages() != null)
            if (news.getImages().size() != 0) {
                news.getImages().forEach(x -> x.setId_news(actualNews.getId()));
                restTemplateService.addImagesNews(news.getImages());
            }
    }

    @Transactional
    public News createNews(News news) {
        return repository.save(news);
    }

    @CachePut(value = "news", key = "#news.getId()")
    public News updateCache(News news) {
        return news;
    }


    public void deleteNews(News news) {repository.delete(news);}
}
