package com.example.push_notification_service.EndPoints;

import javax.websocket.*;
import org.springframework.stereotype.Component;
import java.io.IOException;


@javax.websocket.server.ServerEndpoint(value = "/chat")
@Component
public class MessengerContainer {


    @javax.websocket.OnOpen
    public void onOpen(Session session) {
        System.out.println("Connect session " + session.getId() );
    }

    @javax.websocket.OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message " + message);
    }

    @javax.websocket.OnClose
    public void onClose(Session session) {
    }


}
