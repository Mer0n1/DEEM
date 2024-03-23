package com.example.push_notification_service.controllers;

import com.example.push_notification_service.models.Message;
import com.example.push_notification_service.models.MessagePush;
import com.example.push_notification_service.services.WebSocketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/push")
public class PushController {

    @Autowired
    private WebSocketService webSocketService;

    @PostMapping("/sendMessageToClient")
    public ResponseEntity<?> sendMessageToClient(
            @RequestBody @Valid MessagePush messagePush,
            BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());

        webSocketService.sendMessageToClient(messagePush);

        return ResponseEntity.ok().build();
    }
}
