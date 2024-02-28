package com.example.restful.models.curriculum;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Class {
    private String name;
    private Date date;
    private String type; //l - lecture, p - practice
    private String place; //room

    public Class(String name, Date date, String type, String place) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.place = place;
    }

    public Class() {

    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getPlace() {
        return place;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Class{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", type='" + type + '\'' +
                ", place='" + place + '\'' +
                '}';
    }
}
