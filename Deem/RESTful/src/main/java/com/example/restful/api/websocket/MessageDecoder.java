package com.example.restful.api.websocket;



import com.example.restful.models.Message;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

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

        //message.set(Long.valueOf(jsonObject.getInt("id")));
        message.setAuthor(Long.getLong(jsonObject.getString("author")));
        message.setDate(new Date());
        message.setText(jsonObject.getString("content"));
        return message;
    }

    @Override
    public boolean willDecode(final String s) {
        return true;
    }

}
