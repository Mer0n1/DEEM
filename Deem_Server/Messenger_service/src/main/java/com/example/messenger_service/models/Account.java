package com.example.messenger_service.models;

import jakarta.persistence.*;

public class Account {

    private String username;
    private String password;
    private String name;
    private String surname;
    private String fathername;
    private int score;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getFathername() {
        return fathername;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

}
