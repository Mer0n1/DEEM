package com.example.messenger_service.models;

import lombok.Data;
import java.util.List;

@Data
public class MessagePush {
    private List<Long> receivers;
    private Message message;
}
