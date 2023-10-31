package com.example.news_service.models;

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
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "standart")
    private String type;

    @NotEmpty(message = "Faculty not need to be empty")
    @NotNull(message = "Faculty not need to be null")
    @Column(name = "faculty")
    private String faculty;

    @Column(name = "group_id")
    private Long idGroup;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    @Transient
    private List<String> pathsImg;

}
