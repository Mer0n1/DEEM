package com.example.push_notification_service.models;

import lombok.Data;

import java.util.List;


public class MessagePush {
    private List<Long> receivers;
    private Message message;

    public List<Long> getReceivers() {
        return receivers;
    }

    public Message getMessage() {
        return message;
    }

    public void setReceivers(List<Long> receivers) {
        this.receivers = receivers;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
