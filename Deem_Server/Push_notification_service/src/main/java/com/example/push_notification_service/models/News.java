package com.example.push_notification_service.models;

import lombok.Data;
import lombok.ToString;
import java.util.Date;


@Data
@ToString
public class News {
    private Long id;
    private String content;
    private String faculty;
    private Long idGroup;
    private Long idAuthor;
    private Date date;
    private Integer course;
    private String videoUUID;
}
