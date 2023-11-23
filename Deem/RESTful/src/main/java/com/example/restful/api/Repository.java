package com.example.restful.api;

import com.example.restful.models.Account;
import com.example.restful.models.AuthRequest;
import com.example.restful.models.Chat;
import com.example.restful.models.DataImage;
import com.example.restful.models.Event;
import com.example.restful.models.Group;
import com.example.restful.models.Image;
import com.example.restful.models.Message;
import com.example.restful.models.News;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class Repository {
    private static Repository Instance;

    public static Repository getInstance() {
        if (Instance == null) {
            Instance = new Repository();
        }
        return Instance;
    }

    public Call<String> login(AuthRequest authRequest) {
        return Handler.getInstance().getApi().getToken(authRequest);
    }

    public Call<Account> getMyAccount() {
        return Handler.getInstance().getApi().getMyAccount();
    }

    public Call<Account> getMyAccount(String token) {
        return Handler.getInstance().getApi().getMyAccount("Bearer " + token);
    }

    public Call<List<Account>> getAccounts() {
        return Handler.getInstance().getApi().getAccounts();
    }

    public Call<List<Group>> getGroups() {
        return Handler.getInstance().getApi().getGroups();
    }

    public Call<List<Chat>> getChats() { return Handler.getInstance().getApi().getChats(); }

    public Call<Void> sendMessage(Message message) { return Handler.getInstance().getApi().sendMessage(message); }

    public Call<Void> sendNewChat(Chat chat) { return Handler.getInstance().getApi().sendNewChat(chat); }

    //public Call<ResponseBody> getImageTest() { return Handler.getInstance().getApi().getImage();}

    public Call<List<News>> getNews(String faculty) { return Handler.getInstance().getApi().getNews(faculty); }

    public Call<List<Event>> getEvents() { return Handler.getInstance().getApi().getEvents();}

    public Call<Image> getImage(String UUID) { return Handler.getInstance().getApi().getImage(UUID);}

    public Call<Void> addImages(List<DataImage> imgs) { return Handler.getInstance().getApi().addImages(imgs); }
}
