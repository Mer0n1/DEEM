package com.example.restful.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Time;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    private Long id;
    private String type;
    private Date publication_date;
    private Date start_date;
    private Long idGroup;
    private String faculty;
    private String description;
    private String name;
    private Exam exam;

    public String getNameExam() {
        if (exam == null)
            return name;
        else
            return exam.getName();
    }

    public String getGeneralDescription() {
        return description + "\n" + ((exam == null) ? "" : exam.getDescription());
    }

    public String getType() {
        return type;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", publication_date=" + publication_date +
                ", start_date=" + start_date +
                ", idGroup=" + idGroup +
                ", faculty='" + faculty + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", exam=" + exam +
                '}';
    }
}
