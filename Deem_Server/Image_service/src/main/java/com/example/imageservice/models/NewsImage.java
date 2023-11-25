package com.example.imageservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "image_news")
@Data
public class NewsImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @Column(name = "path")
    private String path;

    @NotEmpty(message = "uuid null")
    @Column(name = "uuid")
    private String uuid;

    @NotNull(message = "id news null")
    @Column(name = "id_news")
    private Long id_news;

    @NotNull(message = "image null")
    @Transient
    private Image image;
}
