package com.example.messenger_service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    //@NotNull(message = "Чат не имеет сообщений")
    @OneToMany(mappedBy = "chat")
    @JsonIgnoreProperties("chat")
    private List<Message> messages;

    //@NotNull(message = "В чате должен быть хотя бы 1 пользователь")
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
