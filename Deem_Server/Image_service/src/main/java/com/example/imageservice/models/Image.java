package com.example.imageservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "image")
@Data
public class Image {

    @Id
    @Column(name = "id")
    private Long id;

    /** Сервер самостоятельно сохраняет путь */
    @JsonIgnore
    @Column(name = "path")
    private String path;

    @NotEmpty(message = "Not Empty")
    @Column(name = "name")
    private String name; //unique?

    @NotEmpty(message = "Not Empty")
    @Column(name = "type")
    private String type; //icon group, profile, or usual image?

    @Transient
    private String imgEncode; //закодированный img который сохраняется в БД изображений
}
