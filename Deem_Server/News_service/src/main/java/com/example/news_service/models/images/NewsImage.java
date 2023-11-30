package com.example.news_service.models.images;

import lombok.Data;

@Data
public class NewsImage {
    private Long id;
    private String uuid;
    private Long id_news;
    private Image image;
}
