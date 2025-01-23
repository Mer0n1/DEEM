package com.example.video_service.utils;

import com.example.video_service.models.VideoMetadata;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    public void validate(VideoMetadata videoMetadata) throws Exception {
        if (videoMetadata.getUuid().isEmpty() || videoMetadata.getType().isEmpty()
        || videoMetadata.getId_dependency() == 0)
            throw new Exception("Данные не могут быть пустыми.");

        if (!(videoMetadata.getType().equals("message_video") ||
            videoMetadata.getType().equals("news_video")))
            throw new Exception("Неверный тип данных.");
    }

    public void validate(String type) throws Exception {
        if (!(type.equals("message_video") || type.equals("news_video")))
            throw new Exception("Неверный тип данных.");
    }
}
