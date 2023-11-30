package com.example.messenger_service.models.Image;

import lombok.Data;

@Data
public class MessageImage {
    private Long id;
    private String uuid;
    private Long id_message;
    private Image image;
}
