package com.example.administrative_service.models;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Group {
    private int course;
    private String name;
    private String faculty;
    private Date date_create;


}
