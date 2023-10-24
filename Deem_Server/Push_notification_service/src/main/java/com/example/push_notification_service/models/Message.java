package com.example.push_notification_service.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Message {

    private Long id;
    private String author;
    private String text;
    private Date date;

    /*@JsonIgnoreProperties("messages")
    private Chat chat;*/
}
