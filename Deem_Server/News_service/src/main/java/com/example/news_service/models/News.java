package com.example.news_service.models;

import com.example.news_service.models.images.NewsImage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "news")
@Data
@ToString
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @NotEmpty
    @Column(name = "faculty")
    private String faculty;

    @NotNull
    @Column(name = "group_id")
    private Long idGroup;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    @NotNull
    @Column(name = "author")
    private Long idAuthor;

    @Column(name = "course")
    private Integer course;

    @Transient
    private List<NewsImage> images;
}
