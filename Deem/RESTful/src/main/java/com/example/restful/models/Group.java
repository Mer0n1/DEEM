package com.example.restful.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Group {

    private Long id;
    private Long chat_id;
    private int course;
    private int score;
    private String name;
    private String faculty;
    private Date date_create;
    private List<Long> users;
    private List<News> news;

    private Integer rank;
    private List<Account> accounts; //устанавливаем после загрузки users

    public Long getId() {
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

    public void setId(Long id) {
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

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void setUsers(List<Long> users) {
        this.users = users;
    }

    public List<Long> getUsers() {
        return users;
    }

    public Long getChat_id() {
        return chat_id;
    }

    public void setChat_id(Long chat_id) {
        this.chat_id = chat_id;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
