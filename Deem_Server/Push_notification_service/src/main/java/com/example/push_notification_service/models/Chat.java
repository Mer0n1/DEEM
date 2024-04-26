package com.example.push_notification_service.models;

import java.util.List;

public class Chat {
    private Long id;
    private List<Long> users;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getUsers() {
        return users;
    }

    public void setUsers(List<Long> users) {
        this.users = users;
    }
}
