package com.example.push_notification_service.services;

import com.example.push_notification_service.EndPoints.MainContainer;
import com.example.push_notification_service.models.Client;
import com.example.push_notification_service.models.EventPush;
import com.example.push_notification_service.models.MessagePush;
import com.example.push_notification_service.models.News;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class WebSocketService {
    private org.glassfish.tyrus.server.Server server;

    public WebSocketService() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                server = new org.glassfish.tyrus.server.Server
                        ("0.0.0.0", 8025, "/ws", MainContainer.class);
                try {
                    server.start();
                    System.out.println("Press any key to stop the server..");
                    new Scanner(System.in).nextLine();
                } catch (javax.websocket.DeploymentException e) {
                    throw new RuntimeException(e);
                } finally {
                    server.stop();
                }
            }
        }).start();

    }

    public void sendMessageToClient(MessagePush messagePush) throws JsonProcessingException {

        //нужно вернуть лист никнеймов людей в чате
        List<String> list = new ArrayList<>();
        Set<Client> clients = MainContainer.clients;
        List<Long> listId = messagePush.getReceivers();
        for (Client client : clients)
            for (Long i : listId)
                if (client.getPersonDetails().getId() == i &
                    client.getPersonDetails().getId() != messagePush.getMessage().getAuthor())
                    list.add(client.getPersonDetails().getUsername());

        if (list.size() == 0 || messagePush.getMessage() == null)
            return;

        //
        Set<Client> clientSet = MainContainer.clients;

        for (int j = 0; j < list.size(); j++) {
            String username = list.get(j);
            Optional<Client> client = clientSet.stream().filter(s -> s.getPersonDetails() //TODO если в чате более 2 человек то не работает, убедиться 
                    .getUsername().equals(username)).findAny();

            if (!client.isEmpty()) {
                String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                        .writeValueAsString(messagePush.getMessage());

                MainContainer.send(client.get().getSession(), jsonMessage, MainContainer.Type.Message);
            }
        }

    }

    public void sendEventToClient(EventPush eventPush) throws JsonProcessingException {
        Set<Client> clients = MainContainer.clients;

        String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(eventPush.getEvent());

        for (Client client : clients) {

            if (eventPush.getType() == EventPush.Extension.course)
                if (client.getPersonDetails().getCourse() == eventPush.getEvent().getCourse())
                    MainContainer.send(client.getSession(), jsonMessage, MainContainer.Type.Event);

            if (eventPush.getType() == EventPush.Extension.faculty)
                if (client.getPersonDetails().getFaculty().equals(eventPush.getEvent().getFaculty()))
                    MainContainer.send(client.getSession(), jsonMessage, MainContainer.Type.Event);

            if (eventPush.getType() == EventPush.Extension.university)
                MainContainer.send(client.getSession(), jsonMessage, MainContainer.Type.Event);

            //TODO
            //if (eventPush.getType() == EventPush.Extension.person) //требуются дополнительные данные id студента или группы
            //if (eventPush.getType() == EventPush.Extension.group);
        }
    }


    public void sendNews(News news) throws JsonProcessingException {

        String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(news);

        Set<Client> clients = MainContainer.clients;

        for (Client client : clients)
            if (!news.getIdAuthor().equals(client.getPersonDetails().getId()))
                if (client.getPersonDetails().getFaculty().equals(news.getFaculty()) &&
                    client.getPersonDetails().getCourse().equals(news.getCourse())) {
                    MainContainer.send(client.getSession(), jsonMessage, MainContainer.Type.News);
                }
    }

}
