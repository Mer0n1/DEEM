package com.example.messenger_service.controllers;

import com.example.messenger_service.dao.ChatDAO;
import com.example.messenger_service.models.Account;
import com.example.messenger_service.models.Chat;
import com.example.messenger_service.models.Message;
import com.example.messenger_service.services.ChatService;
import com.example.messenger_service.services.MessageService;
import com.google.common.io.Resources;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;

import java.util.*;

import static com.example.messenger_service.util.ResponseValidator.getErrors;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatDAO chatDAO;

    /** Возвращает чаты текущего пользователя */
    @GetMapping("/getChats")
    public List<Chat> getChats(Principal principal) {
        return chatService.getListChats(principal.getName());
    }

    @PreAuthorize("hasRole('HIGH')")
    @PostMapping("/linkAccountToGroupChat")
    public ResponseEntity<?> linkAccountToGroupChat(@RequestBody Chat chat) {
        if (chatDAO.saveInAccount_chat(chat))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.badRequest().build();
    }


    @PreAuthorize("hasRole('HIGH')")
    @PostMapping("/createChatGroup")
    public Long createChatGroup() {
        return chatService.save(new Chat()).getId();
    }

}
