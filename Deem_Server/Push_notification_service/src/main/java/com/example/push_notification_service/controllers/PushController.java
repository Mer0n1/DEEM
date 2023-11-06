package com.example.push_notification_service.controllers;

import com.example.push_notification_service.models.Message;
import com.example.push_notification_service.models.MessagePush;
import com.example.push_notification_service.services.WebSocketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/push")
public class PushController {

    @Autowired
    private WebSocketService webSocketService;

    @PostMapping("/sendMessageToClient")
    public void sendMessageToClient(@RequestBody /*@Valid*/ MessagePush messagePush) throws JsonProcessingException {
        System.out.println("sendMessageToClient ");

        System.out.println("hmm " + messagePush.getReceivers());
        webSocketService.sendMessageToClient(messagePush);
    }
}
