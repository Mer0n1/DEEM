package com.example.push_notification_service.controllers;

import com.example.push_notification_service.models.EventPush;
import com.example.push_notification_service.models.MessagePush;
import com.example.push_notification_service.models.News;
import com.example.push_notification_service.services.WebSocketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/push")
@PreAuthorize("hasAnyRole('ADMIN', 'HIGH')")
public class PushController {

    @Autowired
    private WebSocketService webSocketService;

    @PostMapping("/sendMessageToClient")
    public ResponseEntity<?> sendMessageToClient(
            @RequestBody @Valid MessagePush messagePush,
            BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        webSocketService.sendMessageToClient(messagePush);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/sendEventPush")
    public ResponseEntity<?> sendEventToClient(@RequestBody @Valid EventPush eventPush,
                                               BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        try {
            webSocketService.sendEventToClient(eventPush);
            return ResponseEntity.ok().build();
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/sendNews")
    public ResponseEntity<?> sendNews(@RequestBody News news) {

        try {
            webSocketService.sendNews(news);
            return ResponseEntity.ok().build();
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors())
            errorMap.put(error.getField(), error.getDefaultMessage());
        return errorMap;
    }

}
