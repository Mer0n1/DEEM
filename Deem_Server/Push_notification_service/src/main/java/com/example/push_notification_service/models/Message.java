package com.example.push_notification_service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    private Long id;
    private Long author;
    private String text;
    private Date date;
    private Chat chat;
}
