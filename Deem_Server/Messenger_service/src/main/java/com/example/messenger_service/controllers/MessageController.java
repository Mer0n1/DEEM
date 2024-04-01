package com.example.messenger_service.controllers;

import com.example.messenger_service.models.Message;
import com.example.messenger_service.services.MessageService;
import com.example.messenger_service.services.MessengerServiceClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.messenger_service.util.ResponseValidator.getErrors;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private MessengerServiceClient messengerServiceClient;

    @PostMapping("/sendMessage")
    public ResponseEntity<?> sendMessage(@RequestBody @Valid Message message,
                                      BindingResult bindingResult) throws JsonProcessingException {

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        Message actualObject = messageService.save(message);

        if (message.getImages() != null)
            if (message.getImages().size() != 0) {
                message.getImages().forEach(x -> x.setId_message(actualObject.getId()));
                messengerServiceClient.addImagesNews(message.getImages());
            }


        //send
        messengerServiceClient.pushMessageTo(message);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/getMessage")
    public Message getMessage(int id) {
        return messageService.getMessage(id);
    }

    /** Отправить новые сообщения. Обновление чата. Пользователь отправляет
     * дату последнего сообщения в кэше его устройства на сервер, сервер же
     * определяет является ли сообщение последним и если нет отправляет новые
     * сообщения клиенту*/
    @GetMapping("/updateMessages")
    public List<Message> getLastMessages(String dateLastMessage, int idChat) {

        return new ArrayList<>();
    }

}
