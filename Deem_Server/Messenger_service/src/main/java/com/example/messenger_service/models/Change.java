package com.example.messenger_service.models;

public class Change {
    String subject;

    public Change(String sub) {
        subject = sub;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
