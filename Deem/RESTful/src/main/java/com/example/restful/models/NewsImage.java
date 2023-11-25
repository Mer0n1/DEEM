package com.example.restful.models;

public class NewsImage {
    private Long id;
    private String uuid;
    private Long id_news;
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

    public Long getId_news() {
        return id_news;
    }

    public void setId_news(Long id_news) {
        this.id_news = id_news;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
