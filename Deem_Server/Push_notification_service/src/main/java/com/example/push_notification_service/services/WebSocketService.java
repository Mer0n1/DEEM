package com.example.push_notification_service.services;

import com.example.push_notification_service.EndPoints.MainContainer;
import com.example.push_notification_service.models.Client;
import com.example.push_notification_service.models.Message;
import com.example.push_notification_service.models.MessagePush;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class WebSocketService {
    private org.glassfish.tyrus.server.Server server;

    @Autowired
    private RestTemplateService restTemplateService;
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
                if (client.getPersonDetails().getId() == i)
                    list.add(client.getPersonDetails().getUsername());

        if (list.size() == 0 || messagePush.getMessage() == null)
            return;

        //
        Set<Client> clientSet = MainContainer.clients;
        Optional<Client> client = clientSet.stream().filter(s->s.getPersonDetails()
                .getUsername().equals(list.get(0))).findAny();

        if (!client.isEmpty()) {
            String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                    .writeValueAsString(messagePush.getMessage());

            MainContainer.sendMessage(client.get().getSession(), jsonMessage, MainContainer.Type.Message);
        }

    }

}
