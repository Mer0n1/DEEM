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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "content must not be null")
    @Column(name = "content")
    private String content;

    @NotEmpty(message = "type must not be null")
    @Column(name = "standart")
    private String type;

    @NotEmpty(message = "Faculty not need be null")
    @Column(name = "faculty")
    private String faculty;

    @NotNull(message = "id Group must not be null")
    @Column(name = "group_id")
    private Long idGroup;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    @Transient
    private List<String> pathsImg;

}
