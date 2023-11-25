package com.example.restful.models;



public class MessageImage {
    private Long id;
    private String uuid;
    private Long IdMessage;
    private Image image;

    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public Long getIdMessage() {
        return IdMessage;
    }

    public Image getImage() {
        return image;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setIdMessage(Long idMessage) {
        IdMessage = idMessage;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
