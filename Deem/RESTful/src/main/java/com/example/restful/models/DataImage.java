package com.example.restful.models;


public class DataImage {
    private String name;
    private String type;
    private String uuid;
    private Image image;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Image getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "DataImage{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", UUID='" + uuid + '\'' +
                ", image=" + image +
                '}';
    }
}
