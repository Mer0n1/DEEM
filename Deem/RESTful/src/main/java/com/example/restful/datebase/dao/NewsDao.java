package com.example.restful.datebase.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Upsert;

import com.example.restful.models.News;

import java.util.List;

@Dao
public interface NewsDao {
    @Insert
    void insert(News news);

    @Insert
    void insertAll(List<News> newsList);

    @Update
    void updateAll(List<News> newsList);

    @Upsert
    void upsertAll(List<News> newsList);

    @Query("SELECT * FROM news")
    List<News> getAllNews();

    @Query("SELECT * FROM News WHERE id = :newsId")
    News getNewsById(int newsId);

    @Delete
    void delete(News news);
}
