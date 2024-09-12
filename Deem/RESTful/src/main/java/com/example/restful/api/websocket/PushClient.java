package com.example.restful.api.websocket;

import androidx.lifecycle.MutableLiveData;

import com.example.restful.api.APIManager;
import com.example.restful.models.Chat;
import com.example.restful.models.Event;
import com.example.restful.models.Group;
import com.example.restful.models.Message;
import com.example.restful.models.News;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

        Request request = new Request.Builder().url("ws://192.168.0.103:8025/ws/login").build();


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

                if (protocol.equals("\"Message\"")) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        Message message = objectMapper.readValue(jsonObject.get("Type").toString(), Message.class);
                        message.setImages(new MutableLiveData<>());

                        if (message != null) {
                            int id = message.getChat().getId();
                            List<Chat> chats = APIManager.getManager().listChats.getValue();
                            Chat chat = chats.stream().filter(s -> s.getId() == id).findAny().orElse(null);
                            chat.getMessages().add(message);
                            APIManager.getManager().listChats.postValue(chats); //update
                        }
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }


                //Event Protocol
                if (protocol.equals("\"Event\"")) {
                    ObjectMapper objectMapper = new ObjectMapper();

                    try {
                        Event event = objectMapper.readValue(jsonObject.get("Type").toString(), Event.class);

                        //Если есть повторения то выходим
                        for (Event event_ : APIManager.getManager().listEvents.getValue())
                            if (event_.getId() == event.getId())
                                return;

                        List<Event> events = APIManager.getManager().listEvents.getValue();
                        events.add(event);
                        APIManager.getManager().listEvents.postValue(events);

                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }

                //News protocol
                if (protocol.equals("\"News\"")) {
                    ObjectMapper objectMapper = new ObjectMapper();

                    try {
                        News news = objectMapper.readValue(jsonObject.get("Type").toString(), News.class);

                        for (News news_ : APIManager.getManager().listNews.getValue())
                            if (news_.getId() == news.getId())
                                return;

                        Group group = APIManager.getManager().listGroups.stream().filter(
                                x->x.getId().equals(news.getIdGroup())).findAny().orElse(null);
                        if (group == null)
                            return;
                        news.setImages(new MutableLiveData<>());
                        news.setGroup(group);

                        List<News> list = APIManager.getManager().listNews.getValue();
                        list.add(0, news);
                        APIManager.getManager().listNews.postValue(list);

                    } catch (JsonMappingException e) {
                        e.printStackTrace();
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