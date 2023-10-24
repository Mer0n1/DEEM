package com.example.messenger_service.controllers;

import com.example.messenger_service.dao.ChatDAO;
import com.example.messenger_service.models.Chat;
import com.example.messenger_service.services.ChatService;
import com.example.messenger_service.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ChatDAO chatDAO;


    @GetMapping("/getChat")
    public Chat getChat(int id) {
        System.out.println("called getChat " + id);
        return new Chat(); //
    }

    /** Возвращает чаты текущего пользователя */
    @GetMapping("/getChats")
    public List<Chat> getChats(Principal principle) {
        System.out.println("getChats called");

        int accountId = chatDAO.getIdByAccount(principle.getName());
        List<Chat> chats = chatService.getChats(chatDAO.getChatsIdByAccountId(accountId));

        for (Chat chat : chats) //получим id аккаунтов для каждого чата
            chat.setUsers(chatDAO.getIdAccountsOfChat(chat.getId().intValue()));

        System.out.println(chats);
        System.out.println(chats.size());
        return chats;
    }


    /** При создании чата пользователь создает всего 1 сообщение */
    @PostMapping("/createChat")
    public void createChat(@RequestBody Chat chat/*, @Valid BindingResult bindingResult*/) {

        System.out.println(chat.toString());
        //System.out.println(chat.getMessages().get(0).getAuthor());

        
        /*if (bindingResult.hasErrors())
            return;*/

        System.out.println("----------------");

        //создание чата в таблице чатов
        chatService.save(chat);
        //создание сообщения в таблице сообщений
        messageService.save(chat.getMessages().get(0));
        //создание чата в таблице account_chat
        chatDAO.saveInAccount_chat(chat);
    }

}
