package com.example.restful.api.websocket;

import com.example.restful.api.APIManager;
import com.example.restful.models.Chat;
import com.example.restful.models.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.StringReader;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class PushClient {
    private final OkHttpClient client;
    private final WebSocket webSocket;

    public PushClient() {
        client = new OkHttpClient();

        Request request = new Request.Builder().url("ws://192.168.1.104:8025/ws/login").build();


        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSockets, Response response) {
                System.out.println("onOpen");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                System.out.println("onMessage " + text);
                JsonObject jsonObject = JsonParser.parseString(text).getAsJsonObject();
                String protocol = jsonObject.get("Protocol").toString();

                System.out.println(protocol);

                if (protocol.equals("\"Message\"")) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        Message message = objectMapper.readValue(jsonObject.get("Type").toString(), Message.class);
                        System.out.println(jsonObject.get("Type") + " " + message);
                        if (message != null) {
                            System.out.println("message != null");
                            int id = message.getChat().getId();
                            List<Chat> chats = APIManager.getManager().listChats;
                            Chat chat = chats.stream().filter(s -> s.getId() == id).findAny().orElse(null);
                            chat.getMessages().add(message);
                            System.out.println(chat);

                        }
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {

            }
        });

    }


    public void auth(String jwt) {
        String jsonMessage = "{\"Authorization\":\"" + jwt + "\"}";
        webSocket.send(jsonMessage);
    }
}