package com.example.restful.models;

public class Image {
    private String imgEncode;

    public Image(String imgEncode) {
        this.imgEncode = imgEncode;
    }

    public Image() {
    }

    public String getImgEncode() {
        return imgEncode;
    }

    public void setImgEncode(String imgEncode) {
        this.imgEncode = imgEncode;
    }
}
