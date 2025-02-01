package com.example.restful.models;

import java.util.Date;
import java.util.List;

public class CreateMessageDTO {
    private String text;
    private Date date;
    private Long author;
    private List<MessageImage> images;
    private Chat chat;
    private boolean newChat;
    private boolean thereVideo; //isThereVideo
    private String videoUUID;

    public CreateMessageDTO() {}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public List<MessageImage> getImages() {
        return images;
    }

    public void setImages(List<MessageImage> images) {
        this.images = images;
    }

    public boolean isNewChat() {
        return newChat;
    }

    public void setNewChat(boolean newChat) {
        this.newChat = newChat;
    }

    public boolean isThereVideo() {
        return thereVideo;
    }

    public void setThereVideo(boolean thereVideo) {
        this.thereVideo = thereVideo;
    }

    public String getVideoUUID() {
        return videoUUID;
    }

    public void setVideoUUID(String videoUUID) {
        this.videoUUID = videoUUID;
    }
}
