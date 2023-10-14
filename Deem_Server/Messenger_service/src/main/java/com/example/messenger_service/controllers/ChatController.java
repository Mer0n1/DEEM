package com.example.messenger_service.controllers;

import com.example.messenger_service.dao.ChatDAO;
import com.example.messenger_service.models.Chat;
import com.example.messenger_service.services.ChatService;
import jakarta.ws.rs.GET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatDAO chatDAO;


    @GetMapping("/getChat")
    public Chat getChat(int id) {
        System.out.println("called getChat " + id);
        return new Chat(); //
    }

    @GetMapping("/getChats")
    public List<Chat> getChats(Principal principle) {
        System.out.println("getChats called");

        int accountId = chatDAO.getIdByAccount(principle.getName());
        List<Chat> chats = chatService.getChats(chatDAO.getChatsIdByAccountId(accountId));

        return chats;
    }


    //@PostMapping("/createChat")

}
