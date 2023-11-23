package com.example.imageservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "image")
@Data
public class DataImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** Сервер самостоятельно сохраняет путь */
    @JsonIgnore
    @Column(name = "path")
    private String path;

    @NotEmpty(message = "name must not be null")
    @Column(name = "name")
    private String name; //unique?

    @NotEmpty(message = "type must not be null")
    @Column(name = "type")
    private String type; //icon group, profile, or usual image?

    @NotEmpty(message = "uuid null")
    @Column(name = "uuid")
    private String uuid;

    @NotNull(message = "image null")
    @Transient
    private Image image;
}
