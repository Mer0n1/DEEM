package com.example.administrative_service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class Chat {

    /*@JsonIgnoreProperties("chat")
    private List<Message> messages;*/

    private Long id;
    private List<Long> users;


    /*public List<Message> getMessages() {
        return messages;
    }
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }*/

}
