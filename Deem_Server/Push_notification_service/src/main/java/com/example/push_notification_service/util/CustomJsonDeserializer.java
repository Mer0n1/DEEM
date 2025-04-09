package com.example.push_notification_service.util;

import com.example.push_notification_service.models.Message;
import com.example.push_notification_service.models.MessagePush;
import com.example.push_notification_service.models.News;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;


public class CustomJsonDeserializer implements Deserializer<Object> {
    private final ObjectMapper objectMapper;

    public CustomJsonDeserializer() {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);// Игнорируем неизвестные поля
    }

    @Override
    public Object deserialize(String topic, byte[] data) {
        try {
            if ("news_push".equals(topic)) {
                return objectMapper.readValue(data, News.class);
            } else if ("message_push".equals(topic)) {
                return objectMapper.readValue(data, MessagePush.class);
            } else {
                throw new RuntimeException("Unknown topic: " + topic);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing message from topic " + topic, e);
        }
    }
}
