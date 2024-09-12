package com.example.restful.models;

import androidx.lifecycle.MutableLiveData;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.restful.datebase.converters.Converters;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(
        tableName = "message",
        foreignKeys = @ForeignKey(
                entity = Chat.class,
                parentColumns = "id",
                childColumns = "chatId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Message {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "text")
    private String text;
    @ColumnInfo(name = "date")
    @TypeConverters(Converters.class)
    private Date date;
    @ColumnInfo(name = "author")
    private Long author;

    @Ignore
    private Chat chat;
    @Ignore
    private MutableLiveData<List<MessageImage>> images;
    @Ignore
    private boolean NoImages;

    @ColumnInfo(name = "chatId", index = true)
    private Long chatId;

    public Message() {}

    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
        if (chat != null)
            chatId = (long) chat.getId();
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public MutableLiveData<List<MessageImage>> getImages() {
        return images;
    }

    public void setImages(MutableLiveData<List<MessageImage>> images) {
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isNoImages() {
        return NoImages;
    }

    public void setNoImages(boolean noImages) {
        NoImages = noImages;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
