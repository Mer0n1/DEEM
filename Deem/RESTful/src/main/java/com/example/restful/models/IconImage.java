package com.example.restful.models;


public class IconImage {
    private Long id;
    private String uuid;
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
}
