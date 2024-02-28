package com.example.restful.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    private String type;
    private String name;
    private String description;
    private Date publication_date;
    private Date start_date;
    private Long idGroup;
    private String faculty;


    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getPublication_date() {
        return publication_date;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Long getIdGroup() {
        return idGroup;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublication_date(Date publication_date) {
        this.publication_date = publication_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setIdGroup(Long idGroup) {
        this.idGroup = idGroup;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
