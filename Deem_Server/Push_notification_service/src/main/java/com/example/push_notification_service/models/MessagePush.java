package com.example.push_notification_service.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;


public class MessagePush {
    @NotNull
    private List<Long> receivers;

    @NotNull
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

    @Override
    public String toString() {
        return "MessagePush{" +
                "receivers=" + receivers +
                ", message=" + message +
                '}';
    }
}
