package com.example.restful.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.restful.datebase.converters.ConverterListLong;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Chat {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @Ignore
    private List<Message> messages;
    @TypeConverters(ConverterListLong.class)
    private List<Long> users;

    public Chat() {
        messages = new ArrayList<>();
        users = new ArrayList<>();
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public List<Long> getUsers() {
        return users;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsers(List<Long> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", messages=" + messages +
                ", users=" + users +
                '}';
    }
}
