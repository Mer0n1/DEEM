package com.example.push_notification_service.models;

import lombok.Data;

@Data
public class Image {
    private String imgEncode;

    public String getImgEncode() {
        return imgEncode;
    }

    public void setImgEncode(String imgEncode) {
        this.imgEncode = imgEncode;
    }


}
