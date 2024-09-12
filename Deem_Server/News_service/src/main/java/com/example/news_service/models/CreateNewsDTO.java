package com.example.news_service.models;

import com.example.news_service.models.images.NewsImage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class CreateNewsDTO {
    private Long id;
    private String content;
    private Date date;
    private Long idGroup;
    private Long idAuthor;
    private String faculty;
    private List<NewsImage> images;
}
