package com.example.restful.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class VideoMetadata {
    public enum TypeVideoData { message_video, news_video }
    private Long id_dependency;
    private TypeVideoData type;
    private String uuid;

    @JsonIgnore
    private byte[] videoData;

    public VideoMetadata(TypeVideoData type, String uuid, byte[] videoData) {
        this.type = type;
        this.uuid = uuid;
        this.videoData = videoData;
    }

    public VideoMetadata() {}

    public Long getId_dependency() {
        return id_dependency;
    }

    public TypeVideoData getType() {
        return type;
    }

    public String getUuid() {
        return uuid;
    }

    public byte[] getVideo() {
        return videoData;
    }

    public void setId_dependency(Long id_dependency) {
        this.id_dependency = id_dependency;
    }

    public void setType(TypeVideoData type) {
        this.type = type;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setVideo(byte[] video) {
        this.videoData = video;
    }
}
