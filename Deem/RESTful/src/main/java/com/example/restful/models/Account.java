package com.example.restful.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.swing.ImageIcon;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String fathername;
    private int score;
    private int group_id;
    private Group group;

    private Image imageIcon;


    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public Image getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(Image imageIcon) {
        this.imageIcon = imageIcon;
    }
}
