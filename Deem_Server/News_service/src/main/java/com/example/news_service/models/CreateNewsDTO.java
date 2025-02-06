package com.example.news_service.models;

import com.example.news_service.models.images.NewsImage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Long idGroup;
    @NotNull
    private Long idAuthor;
    @NotEmpty
    private String faculty;
    private List<NewsImage> images;
    private Boolean thereVideo;
    private String videoUUID;
}
