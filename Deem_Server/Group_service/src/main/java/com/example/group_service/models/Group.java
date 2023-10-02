package com.example.group_service.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "di_group")
public class Group {

    @Id
    @Column(name = "group_id")
    private int id;

    @Column(name = "faculty")
    private String faculty; //название факультета

    @Column(name = "course")
    private int course; //курс.

    @Column(name = "score")
    private int score;

    @Column(name = "name")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_create")
    private Date date_create;

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
}
