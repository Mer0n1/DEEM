package com.example.restful.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Group {

    private int id;
    private int course;
    private int score;
    private String name;
    private String faculty;
    private Date date_create;
    private List<Account> users;

    public int getId() {
        return id;
    }

    public String getFaculty() {
        return faculty;
    }

    public int getCourse() {
        return course;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public Date getDate_create() {
        return date_create;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate_create(Date date_create) {
        this.date_create = date_create;
    }

    public List<Account> getUsers() {
        return users;
    }

    public void setUsers(List<Account> users) {
        this.users = users;
    }
}
