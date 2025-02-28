package com.example.restful.models;

import androidx.lifecycle.MutableLiveData;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.restful.datebase.converters.Converters;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ColumnInfo(name = "video_uuid")
    private String videoUUID;
    @ColumnInfo(name = "chatId", index = true)
    private Long chatId;
    @ColumnInfo(name = "isRead")
    @JsonIgnore
    private boolean isRead; //todo

    @Ignore
    private Chat chat;
    @Ignore
    @JsonIgnore
    private MutableLiveData<List<MessageImage>> images;
    /** Переменная сохранябщая проверку на изображения для того чтобы не запрашивать
     * у сервера наличие изображений каждый раз*/
    //@Ignore
    @JsonIgnore
    private boolean NoImages;
    /** Переменная обозначающая конечное состояние. Сообщение может быт
     *  созданной или же полностью загруженной/обновленной.
     *  True значение означает, что для этой новости мы не загружаем обновления из сервера и кэша */
    @Ignore
    @JsonIgnore
    private boolean isCompleted;

    /** Параметр использующийся для БД сервера, но ненужный для кэширования*/
    @Ignore
    private boolean isThereVideo;
    /** Параметр нужный для протоколирования sendMessage-затем upload video*/
    @Ignore
    @JsonIgnore
    private VideoMetadata videoMetadata;


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

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getVideoUUID() {
        return videoUUID;
    }

    public void setVideoUUID(String videoUUID) {
        this.videoUUID = videoUUID;
    }

    public boolean getThereVideo() {
        return isThereVideo;
    }

    public void setThereVideo(boolean thereVideo) {
        isThereVideo = thereVideo;
    }

    public VideoMetadata getVideoMetadata() {
        return videoMetadata;
    }

    public void setVideoMetadata(VideoMetadata videoMetadata) {
        this.videoMetadata = videoMetadata;
    }

    public boolean getRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ",Content=" + text +
                ",chatId=" + chatId + " " + chat.getId() +
                '}';
    }
}
