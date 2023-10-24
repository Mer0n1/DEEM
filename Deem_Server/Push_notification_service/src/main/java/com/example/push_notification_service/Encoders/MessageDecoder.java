package com.example.push_notification_service.Encoders;


import com.example.push_notification_service.models.Message;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.StringReader;
import java.util.Date;

public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public void init(final EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public Message decode(final String textMessage) throws DecodeException {
        Message message = new Message();
        JsonObject jsonObject = Json.createReader(new StringReader(textMessage)).readObject();

        message.setId(Long.valueOf(jsonObject.getInt("id")));
        message.setAuthor(jsonObject.getString("author"));
        message.setDate(new Date());
        message.setText(jsonObject.getString("content"));
        return message;
    }

    @Override
    public boolean willDecode(final String s) {
        return true;
    }

}
