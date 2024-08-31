package com.example.restful.datebase.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Upsert;

import com.example.restful.models.Chat;

import java.util.List;

@Dao
public interface ChatDao {
    @Insert
    void insert(Chat chat);

    @Insert
    void insertAll(List<Chat> chatList);

    @Update
    void updateAll(List<Chat> chatList);

    @Upsert
    void upsertAll(List<Chat> chatList);

    @Query("SELECT * FROM chat")
    List<Chat> getAllChats();

    @Query("SELECT * FROM chat WHERE id = :userId")
    Chat getMessageById(int userId);

    @Delete
    void delete(Chat message);
}
