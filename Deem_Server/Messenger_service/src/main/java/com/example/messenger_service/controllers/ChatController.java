package com.example.messenger_service.controllers;

import com.example.messenger_service.dao.ChatDAO;
import com.example.messenger_service.models.Chat;
import com.example.messenger_service.models.Message;
import com.example.messenger_service.services.ChatService;
import com.example.messenger_service.services.MessageService;
import com.google.common.io.Resources;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private MessageService messageService;


    @GetMapping("/getChat")
    public Chat getChat(int id) {
        System.out.println("called getChat " + id);
        return new Chat(); /** */
    }

    /** Возвращает чаты текущего пользователя */
    @GetMapping("/getChats")
    public List<Chat> getChats(Principal principle) {
        System.out.println("getChats called");
        return chatService.getListChats(principle.getName());
    }


    /** При создании чата пользователь создает всего 1 сообщение */
    @PostMapping("/createChat")
    public void createChat(@RequestBody @Valid Chat chat,
                           BindingResult bindingResult) {
        System.out.println("createChat called");

        if (bindingResult.hasErrors())
            return;

        chatService.CreateNewChat(chat);
    }



}
