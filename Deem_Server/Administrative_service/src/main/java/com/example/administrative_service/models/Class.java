package com.example.administrative_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @NotEmpty
    private String name;

    @Max(1)
    @NotEmpty
    private String type; //l - lecture, p - practice

    @NotEmpty
    private String place; //room

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
