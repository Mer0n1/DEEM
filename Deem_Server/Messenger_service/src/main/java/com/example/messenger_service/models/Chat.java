package com.example.messenger_service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "chat")
//@Data
//@ToString
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("chat")
    private List<Message> messages;

    @NotEmpty
    @Transient
    private List<Long> users;


    public Long getId() {
        return id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<Long> getUsers() {
        return users;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void setUsers(List<Long> users) {
        this.users = users;
    }
}
