package com.example.restful.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Exam {

    private Long id;
    private String name;
    private String type;
    private String description;
    private String duration;
    private String addressToExamService;

    private Time time;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }

    public String getAddressToExamService() {
        return addressToExamService;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(String duration) {
        this.duration = duration;

        //TODO
        /*DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        try {
            time = new Time(formatter.parse(duration).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

    }

    public void setAddressToExamService(String addressToExamService) {
        this.addressToExamService = addressToExamService;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", duration='" + duration + '\'' +
                ", AddressToExamService='" + addressToExamService + '\'' +
                ", time=" + time +
                '}';
    }
}
