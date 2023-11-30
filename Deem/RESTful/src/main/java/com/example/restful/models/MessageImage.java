package com.example.restful.models;



public class MessageImage {
    private Long id;
    private String uuid;
    private Long id_message;
    private Image image;

    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
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

    public void setImage(Image image) {
        this.image = image;
    }

    public Long getId_message() {
        return id_message;
    }

    public void setId_message(Long id_message) {
        this.id_message = id_message;
    }
}
