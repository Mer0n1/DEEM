package com.example.messenger_service.models;

import com.example.messenger_service.models.Image.MessageImage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateMessageDTO {
    private Long id;
    @NotEmpty
    private String text;
    private Date date;
    @NotNull
    private Long author;
    @NotNull
    private Chat chat;
    private List<MessageImage> images;
    private boolean newChat;
}
