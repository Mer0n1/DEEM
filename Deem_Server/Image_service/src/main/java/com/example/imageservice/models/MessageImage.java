package com.example.imageservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "image_message")
@Data
public class MessageImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @Column(name = "path")
    private String path;

    @NotEmpty
    @Column(name = "uuid")
    private String uuid;

    @NotNull
    @Column(name = "id_message")
    private Long id_message;

    @NotNull
    @Transient
    private Image image;
}
