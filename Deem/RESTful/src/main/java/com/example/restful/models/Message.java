package com.example.restful.models;

public class Message {
    private String author;
    private String text;
    private String type; //send/receive

    public Message() {

    }

    public Message(String text, String type) {
        this.text = text;
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
