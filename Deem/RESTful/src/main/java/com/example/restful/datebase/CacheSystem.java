package com.example.restful.datebase;

import static com.example.restful.datebase.AppDatabase.MIGRATION_1_2;
import static com.example.restful.datebase.AppDatabase.MIGRATION_2_3;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.example.restful.api.APIManager;
import com.example.restful.api.Repository;
import com.example.restful.datebase.dao.ChatDao;
import com.example.restful.datebase.dao.MessageDao;
import com.example.restful.datebase.dao.NewsDao;
import com.example.restful.models.Chat;
import com.example.restful.models.Image;
import com.example.restful.models.Message;
import com.example.restful.models.MessageImage;
import com.example.restful.models.News;
import com.example.restful.models.NewsImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;

//TODO стоит подумать про очистку кэша и изображений к примеру. Например можно сортировать изменнные/используемые изображения и очистить те который использовались только дня 2-3 назад
//TODO и делаем сохранение изображений сообщений
public class CacheSystem {
    private static CacheSystem cacheSystem;
    private static AppDatabase db;
    private static File FilesDir;

    /** Система отслеживания статусы кэщ-системы. Здесь официально
     * можно отследить загруженные данные из кэша. */
    private static CacheStatusInfo cacheStatusInfo;

    //Dao
    private NewsDao newsDao;
    private MessageDao messageDao;
    private ChatDao chatDao;

    //saved
    /** Данные на которые мы подписались.
     *  Любое обновление списков новостей, сообщений автоматически вызывает
     *  методы, отслеживающие обновления данных. По этим спискам определяются новые обьекты.
     *  Определив новые обьекты мы подписываемся на изменения их списка изображений для сохранения изображений.
     *  Эти данные нужны только для обновления/сохранения изображений.
     * */
    private List<News> newsListSaved;
    private List<Message> messageListSaved;

    private CacheSystem() {
        newsDao    = db.newsDao();
        messageDao = db.messageDao();
        chatDao    = db.chatDao();

        newsListSaved    = new ArrayList<>();
        messageListSaved = new ArrayList<>();
    }

    public static void initialize(Context applicationContext) {
        if (cacheSystem == null) {
            //applicationContext.deleteDatabase("deemdatabase.db"); //TEST TODO

            db = Room.databaseBuilder(applicationContext,
                    AppDatabase.class, "deemdatabase.db")
                    .addMigrations(MIGRATION_1_2)
                    .addMigrations(MIGRATION_2_3)
                    .build();
            FilesDir = applicationContext.getFilesDir();

            cacheSystem = new CacheSystem();
            cacheStatusInfo = new CacheStatusInfo();
        }
    }

    public void observeData() {
        //LiveData
        //Подписываемся на обновления данных
        APIManager.getManager().getListNews().observeForever(new Observer<List<News>>() { //TODO removeObserver(Observer)
            @Override
            public void onChanged(List<News> newsList) {
                saveNews();
            }
        });

        APIManager.getManager().getListChats().observeForever(new Observer<List<Chat>>() {
            @Override
            public void onChanged(List<Chat> chats) {
                CompletableFuture //важно чтобы chats выполнилось первым
                        .runAsync(new Runnable() {
                            @Override
                            public void run() {
                                saveChats();
                            }
                        })
                        .thenRun(new Runnable() {
                            @Override
                            public void run() {
                                saveMessages();
                            }
                        });
            }
        });
    }

    public void saveAll() {
        saveNews();
        saveChats();
        saveMessages();
    }


    public void loadAll() {
        newThread(()-> {
            //News
            List<News> newsList = newsDao.getAllNews();
            Collections.reverse(newsList);
            newsList.stream().forEach(x->x.setImages(new MutableLiveData<>()));
            APIManager.getManager().getListNews().postValue(newsList);
            APIManager.statusCacheInfo.ListNewsLoaded = true;

            //Chats
            List<Chat> chatList = chatDao.getAllChats();

            for (Chat chat : chatList) {
                chat.setMessages(messageDao.getMessagesByIdChat(chat.getId()));

                for (Message message : chat.getMessages()) {
                    message.setChat(chat);
                    message.setImages(new MutableLiveData<>());
                    message.getImages().postValue(new ArrayList<>());
                }
            }
            APIManager.getManager().getListChats().postValue(chatList);
            cacheStatusInfo.ListChatsLoaded = true;
        });
    }

    public void saveMessages() {
        newThread(()-> {
            if (APIManager.getManager().getListChats().getValue() != null)
                for (Chat chat : APIManager.getManager().getListChats().getValue()) {
                    System.err.println("--- " + chat.getMessages().toString());
                    messageDao.upsertAll(chat.getMessages());
                }
        });

        //Сохранение изображений
        for (Chat chat : APIManager.getManager().getListChats().getValue())
            if (APIManager.getManager().getListChats() != null &&
                    APIManager.getManager().getListChats().getValue() != null) {
                Log.d("saveMessages processing: ", "starting");

                List<Message> notSavedList = new ArrayList<>();
                List<Message> currentList = chat.getMessages();

                for (Message message : currentList)
                    if (!messageListSaved.contains(message))
                        notSavedList.add(message);

                for (Message message : notSavedList)
                    message.getImages().observeForever(new Observer<List<MessageImage>>() {
                        @Override
                        public void onChanged(List<MessageImage> messageImages) {
                            saveImagesChat(messageImages);
                        }
                    });
            }

    }

    public void saveChats() {
        newThread(()-> chatDao.upsertAll(APIManager.getManager().getListChats().getValue()));
    }

    /** Обновляем список новостей в БД и отдельно сохраняем изображения на устройство
     * */
    public void saveNews() {
        newThread(()-> newsDao.upsertAll(APIManager.getManager().getListNews().getValue()));

        //Сохранение изображений
        if (APIManager.getManager().getListNews() != null && APIManager.getManager().getListNews().getValue() != null) {
            Log.d("saveNews processing: ", "starting");

            List<News> notSavedList = new ArrayList<>(); //подписываемся только на новые (неподписанные) новости
            List<News> currentList = APIManager.getManager().getListNews().getValue();

            for (News news : currentList)
                if (!newsListSaved.contains(news))
                    notSavedList.add(news);

            for (News news : notSavedList) {
                news.getImages().observeForever(new Observer<List<NewsImage>>() {
                    @Override
                    public void onChanged(List<NewsImage> newsImages) {
                        saveImagesNews(newsImages);
                    }
                });
            }
        }

        newsListSaved = new ArrayList<>(APIManager.getManager().getListNews().getValue());
    }

    //images
    /** Сохранение изображения с названиями их uuid */
    public void saveImagesChat(List<MessageImage> messageImages) {
        Log.d("saveImagesChat processing: ", "starting");
        for (MessageImage image : messageImages)
            try {
                String path = FilesDir.getPath() + "/" + image.getUuid() + ".png";
                File file = new File(path);
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(Base64.getDecoder().decode(image.getImage().getImgEncode()));
                outputStream.close();
                Log.d("saveImagesChat result: ", "success " + image.getUuid() + "  " +
                        Base64.getDecoder().decode(image.getImage().getImgEncode()).length + " " + path);
            } catch (Exception e) {
                System.err.println("error " + e.getMessage());
            }
    }

    public void saveImagesNews(List<NewsImage> newsImages) {
        Log.d("saveImagesNews processing: ", "starting");
        for (NewsImage image : newsImages)
            try {
                if (image.getImage().getImgEncode() == null)
                    if (image.getImage().getImgEncode().length() == 0)
                        continue;

                String path = FilesDir.getPath() + "/" + image.getUuid() + ".png";
                File file = new File(path);
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(Base64.getDecoder().decode(image.getImage().getImgEncode()));
                outputStream.close();
                Log.d("saveImagesNews result: ", "success " +image.getUuid() + "  " +
                        Base64.getDecoder().decode(image.getImage().getImgEncode()).length + " " + path);

            } catch (Exception e) {
                System.err.println("error " + e.getMessage());
            }
    }

    //загрузка сохраненного в кэше изображения из файловой системы
    public Image getImageByUuid(String uuid) {
        File imageFile = new File(FilesDir, uuid + ".png");

        try {
            if (imageFile.exists()) {
                byte[] fileBytes = Files.readAllBytes(imageFile.toPath());
                String encodedString = Base64.getEncoder().encodeToString(fileBytes);
                return new Image(encodedString);
            }
        } catch (Exception e) {
            return null;
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

    public static CacheSystem getCacheSystem() {
        return cacheSystem;
    }

}
