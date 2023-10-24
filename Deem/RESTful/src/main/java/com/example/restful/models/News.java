package com.example.restful.models;

import java.util.Date;

public class News {
    private String author; //this maybe a name of group
    private String content;

    private Date date;
    //img


    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
