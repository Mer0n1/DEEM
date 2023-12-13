package com.example.auth_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;


public class LocationStudent {
    private Long idStudent;
    private String faculty;
    private int course;

    public Long getIdStudent() {
        return idStudent;
    }

    public String getFaculty() {
        return faculty;
    }

    public int getCourse() {
        return course;
    }

    public void setIdStudent(Long idStudent) {
        this.idStudent = idStudent;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setCourse(int course) {
        this.course = course;
    }
}
