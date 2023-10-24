package com.example.messenger_service.controllers;

import com.example.messenger_service.models.Message;
import com.example.messenger_service.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody Message message) {
        System.out.println("Message called");
        System.out.println(message.toString());

        messageService.save(message);

        //send
        //...

    }

    @GetMapping("/getMessage")
    public Message getMessage(int id) {
        System.out.println(id);
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
