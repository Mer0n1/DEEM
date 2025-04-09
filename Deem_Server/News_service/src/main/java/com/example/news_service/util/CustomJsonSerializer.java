package com.example.news_service.util;

import com.example.news_service.models.News;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.kafka.common.serialization.Serializer;

/*public class CustomJsonSerializer<T> implements Serializer<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, T data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing message", e);
        }
    }
}*/
public class CustomJsonSerializer implements Serializer<News> {
    private final ObjectMapper objectMapper;

    public CustomJsonSerializer() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public byte[] serialize(String topic, News data) {
        try {
            News copy = new News();
            copy.setId(data.getId());
            copy.setContent(data.getContent());
            copy.setFaculty(data.getFaculty());
            copy.setIdGroup(data.getIdGroup());
            copy.setDate(data.getDate());
            copy.setIdAuthor(data.getIdAuthor());
            copy.setCourse(data.getCourse());
            copy.setVideoUUID(data.getVideoUUID());
            copy.setThereVideo(data.getThereVideo());

            return objectMapper.writeValueAsBytes(copy);
        } catch (Exception e) {
            return null;
        }
    }
}

