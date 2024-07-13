package com.example.push_notification_service.models;

import lombok.Data;
import lombok.ToString;

import java.sql.Time;


@Data
@ToString
public class Exam {
    private Long id;
    private String name;
    private String type;
    private String description;
    private Time duration;
    private String AddressToExamService;

}
