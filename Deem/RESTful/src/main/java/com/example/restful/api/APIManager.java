package com.example.restful.api;


import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.restful.datebase.CacheStatusInfo;
import com.example.restful.datebase.CacheSystem;
import com.example.restful.models.Account;
import com.example.restful.models.AuthRequest;
import com.example.restful.models.Chat;
import com.example.restful.models.Club;
import com.example.restful.models.CreateMessageDTO;
import com.example.restful.models.CreateNewsDTO;
import com.example.restful.models.Event;
import com.example.restful.models.Group;
import com.example.restful.models.IconImage;
import com.example.restful.models.ImageLoadCallback;
import com.example.restful.models.Message;
import com.example.restful.models.News;
import com.example.restful.models.StandardCallback;
import com.example.restful.models.TopLoadCallback;
import com.example.restful.models.TopsUsers;
import com.example.restful.models.VideoCallback;
import com.example.restful.models.VideoMetadata;
import com.example.restful.models.curriculum.DayliSchedule;
import com.example.restful.utils.ConverterDTO;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** Менеджер api для связи с данными. Более абстрактный уровень чем repository.
 * Отвечает за статусы получения данных, за авторизацию, регистрацию, отключения. */
public class APIManager {

    private static APIManager manager;
    public  static ServerStatusInfo statusInfo;
    public  static CacheStatusInfo statusCacheInfo;
    /** Сервис вебсокетов запускается согласно протоколу авторизации в методе auth */
    private static PushClient pushClient;

    private String jwtKey;

    public APIManager() {
        pushClient = new PushClient();
        statusInfo = Repository.getInstance().getStatusInfo();
        statusCacheInfo = CacheSystem.getCacheStatusInfo();
    }

    public static APIManager getManager() {
        if (manager == null)
            manager = new APIManager();

        return manager;
    }

    public void auth(AuthRequest authRequest) {

        final Call<String> str = ServerRepository.getInstance().login(authRequest);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Response<String> stri = null;
                try {
                    stri = str.execute();
                    Handler.setToken(stri.body());
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                jwtKey = stri.body();
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pushClient.auth(jwtKey);
        Repository.getInstance().UpdateData();
        Repository.getInstance().getMyAccount();
    }


    public void sendMessage(Message message, boolean isNewChat) {
        CreateMessageDTO dto = ConverterDTO.MessageToCreateMessageDTO(message);
        dto.setNewChat(isNewChat);
System.err.println("+++++++++++ " + dto.getDate() + " " + dto.isNewChat() + " " + dto.getChat() + " " + dto.getText());
        ServerRepository.getInstance().sendMessage(dto).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) { //TODO проверить на Failure вариант
                //в случае удачи если у нас есть видео то отправляем видео в видеосервис
                if (response.isSuccessful() && message.getThereVideo()) {
                    Long id = response.body();

                    if (id != null && id != 0) {
                        message.getVideoMetadata().setId_dependency(id);
                        sendVideo(message.getVideoMetadata());
                    }
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {}
        });
    }

    public void sendNewChat(Chat chat) {
        ServerRepository.getInstance().sendNewChat(chat).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {}

            @Override
            public void onFailure(Call<Void> call, Throwable t) {}
        });
    }

    public void addNews(News news) {
        CreateNewsDTO dto = ConverterDTO.NewsToCreateNewsDTO(news);

        ServerRepository.getInstance().createNews(dto).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {

                if (response.isSuccessful() && news.isThereVideo()) {
                    Long id = response.body();

                    if (id != null && id != 0) {
                        news.getVideoMetadata().setId_dependency(id);
                        sendVideo(news.getVideoMetadata());
                    }
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {}
        });
    }

    public void addIcon(IconImage img) {
        ServerRepository.getInstance().addIcon(img).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {}

            @Override
            public void onFailure(Call<Void> call, Throwable t) {}
        });
    }

    public void sendVideo(VideoMetadata videoMetadata) {
        Repository.getInstance().sendVideo(videoMetadata);
    }


    //------------------------------------------------------------------------
    public void updateNewsFeed(StandardCallback callback) {
        Repository.getInstance().updateNewsFeed(callback);
    }

    public void updateMessagesFeed(StandardCallback callback, Long chatId) {
        Repository.getInstance().updateMessagesFeed(callback, chatId);
    }

    public void getNewsImagesLazy(News news, ImageLoadCallback imageLoadCallback) {
        Repository.getInstance().getNewsImagesLazy(news, imageLoadCallback);
    }

    public void getIconImageLazy(Account account, ImageLoadCallback imageLoadCallback) {
        Repository.getInstance().getIconImageLazy(account, imageLoadCallback);
    }

    public void getMessageImagesLazy(Message message, ImageLoadCallback imageLoadCallback) {
        Repository.getInstance().getMessageImagesLazy(message, imageLoadCallback);
    }

    public void getTopStudentsFaculty(TopLoadCallback callback) {
        Repository.getInstance().getTopStudentsFaculty(callback);
    }

    public void getTopStudentsUniversity(TopLoadCallback callback) {
        Repository.getInstance().getTopStudentsUniversity(callback);
    }

    public void getVideoManifest(VideoCallback callback, String VideoUUID) {
        Repository.getInstance().getVideo(callback, VideoUUID);
    }

    public void getVideoUrl(VideoCallback callback, String VideoUUID) {
        callback.loadVideo(Base.BASE_URL + "videos/" + VideoUUID + "/output.m3u8"); //TODO test
    }


    public String getJwtKey() {
        return jwtKey;
    }

    public Repository getData() { return Repository.getInstance(); }

    public Account getMyAccount() { return Repository.getInstance().myAccount; }

    public List<Account> getListAccounts() { return Repository.getInstance().listAccounts; }

    public List<Group> getListGroups() {
        return Repository.getInstance().listGroups;
    }

    public List<Group> getListGroupsOfFaculty() {
        return Repository.getInstance().listGroupsOfFaculty;
    }

    public List<Group> getAdminGroups() {
        return Repository.getInstance().adminGroups;
    }

    public MutableLiveData<List<Chat>> getListChats() {
        return Repository.getInstance().listChats;
    }

    public MutableLiveData<List<News>> getListNews() {
        return Repository.getInstance().listNews;
    }

    public MutableLiveData<List<Event>> getListEvents() {
        return Repository.getInstance().listEvents;
    }

    public MutableLiveData<List<Club>> getListClubs() {
        return Repository.getInstance().listClubs;
    }

    public List<DayliSchedule> getDayliSchedules() {
        return Repository.getInstance().dayliSchedules;
    }

    public TopsUsers getTopsUsers() {
        return Repository.getInstance().topsUsers;
    }

    public List<Group> getGroupClubs() {
        return Repository.getInstance().groupClubs;
    }
}

