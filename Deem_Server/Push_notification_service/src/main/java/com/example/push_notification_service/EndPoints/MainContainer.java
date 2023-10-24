package com.example.push_notification_service.EndPoints;


import com.example.push_notification_service.Encoders.MessageDecoder;
import com.example.push_notification_service.Encoders.MessageEncoder;
import com.example.push_notification_service.models.Message;


import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

/** Класс для идентификации пользователя. */
@javax.websocket.server.ServerEndpoint(value = "/login", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class MainContainer {
    static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

    /*
    * Должен пронять токен и проверить его
    * Основной класс, все сессии этого сервиса хранятся в нем
    * */

    @javax.websocket.OnOpen
    public void onOpen(Session session) {
        System.out.println(format("Connect ", session.getId()));
        clients.add(session);
    }

    @javax.websocket.OnMessage
    public void onMessage(Message message, Session session) throws IOException, EncodeException {
        System.out.println("message " + message.getText());
    }

    @javax.websocket.OnClose
    public void onClose(Session session) throws IOException, EncodeException {

    }

}

