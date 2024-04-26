package com.example.push_notification_service.models;

import lombok.Data;

@Data
public class MessageImage {
    private Long id;
    private String uuid;
    private Long id_message;
    private Image image;
}
