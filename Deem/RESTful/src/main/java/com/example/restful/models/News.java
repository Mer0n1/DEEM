package com.example.restful.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.restful.datebase.Converters;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class News {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "content")
    private String content;
    @ColumnInfo(name = "date")
    @TypeConverters(Converters.class)
    private Date date;
    @ColumnInfo(name = "idGroup")
    private Long idGroup;
    @ColumnInfo(name = "faculty")
    private String faculty;
    //private String author; //name of group


    @Ignore
    private List<NewsImage> images;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) { this.date = date; }

    public Long getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Long idGroup) {
        this.idGroup = idGroup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public List<NewsImage> getImages() {
        return images;
    }

    public void setImages(List<NewsImage> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", idGroup=" + idGroup +
                ", faculty='" + faculty + '\'' +
                ", images=" + images +
                '}';
    }
}
