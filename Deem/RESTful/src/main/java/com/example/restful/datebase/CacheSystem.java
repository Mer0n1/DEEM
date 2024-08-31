package com.example.restful.datebase;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.WorkerThread;
import androidx.room.Room;

import com.example.restful.api.APIManager;
import com.example.restful.datebase.dao.ChatDao;
import com.example.restful.datebase.dao.MessageDao;
import com.example.restful.datebase.dao.NewsDao;
import com.example.restful.models.Chat;
import com.example.restful.models.Image;
import com.example.restful.models.Message;
import com.example.restful.models.MessageImage;
import com.example.restful.models.News;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class CacheSystem {
    private static CacheSystem cacheSystem;
    private static AppDatabase db;
    private static CacheStatusInfo cacheStatusInfo;

    private static File FilesDir;

    //Dao
    private NewsDao newsDao;
    private MessageDao messageDao;
    private ChatDao chatDao;

    private CacheSystem() {
        newsDao    = db.newsDao();
        messageDao = db.messageDao();
        chatDao    = db.chatDao();
    }

    public static void initialize(Context applicationContext) {
        //applicationContext.deleteDatabase("deemdatabase.db"); //TEST TODO

        db = Room.databaseBuilder(applicationContext,
                AppDatabase.class, "deemdatabase.db").build();
        FilesDir = applicationContext.getFilesDir();
    }

    public static CacheSystem getCacheSystem() {
        if (cacheSystem == null) {
            cacheSystem = new CacheSystem();
            cacheStatusInfo = new CacheStatusInfo();
        }
        return cacheSystem;
    }

    //@WorkerThread
    public void saveAll() {

        newThread(()->
        {
            saveNews();
            saveChats();
            saveMessages();
        });
    }
    //@WorkerThread
    public void loadAll() {
        newThread(()-> {
            //News
            APIManager.getManager().listNews = newsDao.getAllNews();
            Collections.reverse(APIManager.getManager().listNews);
            APIManager.statusCacheInfo.ListNewsLoaded = true;

            //Chats
            APIManager.getManager().listChats = chatDao.getAllChats();

            //Messages
            for (Chat chat : APIManager.getManager().listChats) {
                chat.setMessages(messageDao.getMessagesByIdChat(chat.getId()));

                for (Message message : chat.getMessages())
                    message.setChat(chat);
            }
            cacheStatusInfo.ListChatsLoaded = true;

        });
    }

    public void saveMessages() {
        newThread(()-> {
            for (Chat chat : APIManager.getManager().listChats) {
                messageDao.upsertAll(chat.getMessages());

                //сохраняем изображения //не будет вызываться потому что изображения загружаются отдельно через callback
                /*for (Message message : chat.getMessages())
                    for (MessageImage img : message.getImages())
                        if (!img.getUuid().isEmpty()) {
                            try {
                                File imageFile = new File(FilesDir, img.getUuid());
                                FileOutputStream fos = new FileOutputStream(imageFile);
                                fos.write(img.getImage().getImgEncode().getBytes());
                                System.out.println("success");
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }*/
            }
        });

    }

    public void saveChats() {
        newThread(()-> chatDao.upsertAll(APIManager.getManager().listChats));
    }

    public void saveNews() {
        newsDao.upsertAll(APIManager.getManager().listNews);
    }

    //загрузка сохраненного в кэше изображения из файловой системы
    public Image getImageByUuid(String uuid) throws IOException {
        File imageFile = new File(FilesDir, uuid);

        if (imageFile.exists()) {
            byte[] fileBytes = Files.readAllBytes(imageFile.toPath());
            String encodedString = Base64.getEncoder().encodeToString(fileBytes);
            return new Image(encodedString);
        }
        return null;
    }

    //некоторый метод который запустит любую лямбду в потоке
    interface function {
        void run();
    }
    private void newThread(function func) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                func.run();
            }
        }).start();
    }

    public static CacheStatusInfo getCacheStatusInfo() {
        return cacheStatusInfo;
    }
}
