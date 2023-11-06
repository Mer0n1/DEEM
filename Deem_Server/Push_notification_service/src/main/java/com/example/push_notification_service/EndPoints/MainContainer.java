package com.example.push_notification_service.EndPoints;

import com.example.push_notification_service.config.JWTFilter;
import com.example.push_notification_service.config.PersonDetails;
import com.example.push_notification_service.models.Client;
import org.json.JSONObject;
import org.springframework.stereotype.Component;


import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.Session;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;

/** Класс для идентификации пользователя. */
@javax.websocket.server.ServerEndpoint(value = "/login")
@Component
public class MainContainer {
    public static final Set<Client> clients = Collections.synchronizedSet(new HashSet<Client>());
    /** Неавторизированные клиенты */
    public static final Set<Client> queue  = Collections.synchronizedSet(new HashSet<Client>());

    public enum Type { Message, GroupNotice }

    @javax.websocket.OnOpen
    public void onOpen(Session session) {
        System.out.println(format("Connect " + session.getId()));
        queue.add(new Client(session));
    }

    @javax.websocket.OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("message " + message);

        JsonObject jsonObject = Json.createReader(new StringReader(message)).readObject();
        String auth = jsonObject.getString("Authorization");

        if (!auth.isEmpty()) { //принимает jwt ключ
            //проверим ключ и создадим Principle
            PersonDetails personDetails = JWTFilter.createPersonDetails(auth);

            //
            Optional<Client> client = findInQueue(session);
            if (!client.isEmpty()) {
                client.get().setPersonDetails(personDetails);
                clients.add(client.get());
                queue.remove(client.get());
            }
        }
    }

    @javax.websocket.OnClose //dont work
    public void onClose(Session session) {
        clients.remove(findInClientsList(session));
        queue  .remove(findInQueue(session));
        System.out.println("Close " + clients.size() + " " + queue.size());
    }

    public static void sendMessage(Session session, String jsonMessage, Type type) {
        System.out.println("try sendMessage");

        if (type == Type.Message) {
            jsonMessage = "{\"Protocol\":\"Message\",\"Type\":" + jsonMessage + "}";
        }

        if (session.isOpen()) {

            try {
                session.getBasicRemote().sendText(jsonMessage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Optional<Client> findInQueue(Session session) {
        return queue.stream().filter(s->s.getSession().getId()==session.getId()).findAny();
    }
    public Optional<Client> findInClientsList(Session session) {
        return clients.stream().filter(s->s.getSession().getId()==session.getId()).findAny();
    }
}

