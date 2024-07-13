package com.example.push_notification_service.models;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Event {

    private Long id;
    private String faculty;
    private int course;
    private String name;
    private Date publication_date;
    private Date start_date;
    private Long idGroup;
    private String description;
    private Exam exam;
}
