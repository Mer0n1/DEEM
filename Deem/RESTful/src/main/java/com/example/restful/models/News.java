package com.example.restful.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class News {
    private Long id;
    //private String author; //name of group
    private String content;
    private Date date;
    private Long idGroup;

    private List<Image> ListImg;

    /*public String getAuthor() {
        return author;
    }*/

    public String getContent() {
        return content;
    }

    /*public void setAuthor(String author) {
        this.author = author;
    }*/

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Image> getListImg() {
        return ListImg;
    }

    public void setListImg(List<Image> listImg) {
        ListImg = listImg;
    }

    public Long getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Long idGroup) {
        this.idGroup = idGroup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
