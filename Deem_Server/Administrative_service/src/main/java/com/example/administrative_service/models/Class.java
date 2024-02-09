package com.example.administrative_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Class {
    private Long id;

    @NotEmpty(message = "name must not be null")
    private String name;

    @NotEmpty(message = "type must not be null")
    private String type; //l - lecture, p - practice

    @NotEmpty(message = "place must not be null")
    private String place; //room

    @NotNull(message = "date must not be null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
