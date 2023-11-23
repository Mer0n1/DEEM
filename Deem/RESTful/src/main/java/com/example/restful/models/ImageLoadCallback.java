package com.example.restful.models;


public interface ImageLoadCallback {
    //void onImagesLoaded(List<Bitmap> images);
    void onImageLoaded(String decodeStr);
}
