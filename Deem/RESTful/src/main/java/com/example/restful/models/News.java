package com.example.restful.models;

import androidx.lifecycle.MutableLiveData;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.restful.datebase.converters.Converters;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import kotlin.jvm.Transient;

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
    @ColumnInfo(name = "author")
    private Long idAuthor;
    @ColumnInfo(name = "faculty")
    private String faculty;

    @Ignore
    @JsonIgnore
    private MutableLiveData<List<NewsImage>> images;
    @Ignore
    @JsonIgnore
    private transient Group group;
    /** Переменная обозначающая конечное состояние новости. Новость может быт
     *  созданной или же полностью загруженной/обновленной. True значение
     *  означает, что для этой новости мы не загружаем обновления из сервера и кэша */
    @Ignore
    @JsonIgnore
    private boolean isCompleted;
    /** Переменная сохранябщая проверку на изображения для того чтобы не запрашивать
     * у сервера наличие изображений каждый раз*/
    @JsonIgnore
    private boolean NoImages;

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

    public MutableLiveData<List<NewsImage>> getImages() {
        return images;
    }

    public void setImages(MutableLiveData<List<NewsImage>> images) {
        this.images = images;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Long getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(Long idAuthor) {
        this.idAuthor = idAuthor;
    }

    public boolean isNoImages() {
        return NoImages;
    }

    public void setNoImages(boolean noImages) {
        NoImages = noImages;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", idGroup=" + idGroup +
                ", idAuthor=" + idAuthor +
                ", faculty='" + faculty + '\'' +
                ", images=" + images +
                ", NoImages=" + NoImages +
                '}';
    }
}
