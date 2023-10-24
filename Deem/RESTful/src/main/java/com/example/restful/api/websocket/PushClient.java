package com.example.restful.api.websocket;


import com.example.restful.models.Message;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.text.SimpleDateFormat;

@javax.websocket.ClientEndpoint(encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class PushClient {

    //private SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("connect " + session.isOpen());
    }

    @OnMessage
    public void onMessage(Message message) {
        //System.out.println("message " + message.getAuthor() + " " + message.getText());
    }

}