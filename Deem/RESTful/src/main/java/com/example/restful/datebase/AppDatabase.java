package com.example.restful.datebase;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.restful.datebase.dao.ChatDao;
import com.example.restful.datebase.dao.MessageDao;
import com.example.restful.datebase.dao.NewsDao;
import com.example.restful.models.Chat;
import com.example.restful.models.Message;
import com.example.restful.models.News;

@Database(entities = {News.class, Chat.class, Message.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
    public abstract ChatDao chatDao();
    public abstract MessageDao messageDao();

    /*static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Здесь добавляем SQL запрос для изменения схемы без удаления данных
            database.execSQL("CREATE TABLE new_table (id INTEGER, name TEXT, PRIMARY KEY(id))");
        }
    };*/

}
