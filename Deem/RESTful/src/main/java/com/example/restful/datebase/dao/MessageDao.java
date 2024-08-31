package com.example.restful.datebase.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Upsert;

import com.example.restful.models.Message;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert
    void insert(Message message);

    @Insert
    void insertAll(List<Message> messageList);

    @Update
    void updateAll(List<Message> messageList);

    @Upsert
    void upsertAll(List<Message> messageList);

    @Query("SELECT * FROM message")
    List<Message> getAllMessages();

    @Query("SELECT * FROM message WHERE chatId = :chatId")
    List<Message> getMessagesByIdChat(int chatId);

    @Query("SELECT * FROM Message WHERE id = :messageId")
    Message getMessageById(int messageId);

    @Delete
    void delete(Message message);
}
