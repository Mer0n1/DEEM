package com.example.restful.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Chat {

    private int id;
    private List<Message> messages;
    private List<Long> users;

    public Chat() {
        messages = new ArrayList<>();
        users = new ArrayList<>();
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public List<Long> getUsers() {
        return users;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsers(List<Long> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", messages=" + messages +
                ", users=" + users +
                '}';
    }
}
