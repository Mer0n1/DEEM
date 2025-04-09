package com.example.messenger_service.util;

import com.example.messenger_service.models.Chat;
import com.example.messenger_service.models.Message;
import com.example.messenger_service.models.MessagePush;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class CustomJsonSerializer implements Serializer<MessagePush> {
    private final ObjectMapper objectMapper;

    public CustomJsonSerializer() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public byte[] serialize(String topic, MessagePush data) {
        try {
            MessagePush copy = new MessagePush();
            copy.setReceivers(data.getReceivers());

            Message dataMessage = data.getMessage();
            Chat dataChat = dataMessage.getChat();

            Chat chat = new Chat();
            chat.setId(dataChat.getId());
            chat.setUsers(dataChat.getUsers());

            Message message = new Message();
            message.setId(dataMessage.getId());
            message.setText(dataMessage.getText());
            message.setDate(dataMessage.getDate());
            message.setAuthor(dataMessage.getAuthor());
            message.setVideoUUID(dataMessage.getVideoUUID());
            message.setThereVideo(dataMessage.getThereVideo());
            message.setChat(chat);

            copy.setMessage(message);

            return objectMapper.writeValueAsBytes(copy);
        } catch (Exception e) {
            return null;
        }
    }
}

