package com.example.push_notification_service.EndPoints;

import com.example.push_notification_service.Encoders.MessageDecoder;
import com.example.push_notification_service.Encoders.MessageEncoder;
import com.example.push_notification_service.models.Message;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import javax.websocket.EncodeException;
import java.io.IOException;


@javax.websocket.server.ServerEndpoint(value = "/chat")
public class MessengerEndPoint {


    @javax.websocket.OnOpen
    public void onOpen(javax.websocket.Session session) {
        System.out.println("Connect session");
    }

    @javax.websocket.OnMessage
    public void onMessage(String message, javax.websocket.Session session) throws IOException, javax.websocket.EncodeException {
        System.out.println("Message " + message);
    }

    @javax.websocket.OnClose
    public void onClose(javax.websocket.Session session) throws IOException, EncodeException {
    }

}
