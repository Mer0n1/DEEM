package com.example.restful.models;

import java.util.Date;
import java.util.List;

public class ReceiveMessageDto {
    private Integer id;
    private String text;
    private Date date;
    private Long author;
    private List<MessageImage> images;
    private Chat chat;
    private String videoUUID;

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public Long getAuthor() {
        return author;
    }

    public List<MessageImage> getImages() {
        return images;
    }

    public Chat getChat() {
        return chat;
    }

    public String getVideoUUID() {
        return videoUUID;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public void setImages(List<MessageImage> images) {
        this.images = images;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public void setVideoUUID(String videoUUID) {
        this.videoUUID = videoUUID;
    }
}
