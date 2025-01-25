package com.example.restful.api;

import com.example.restful.models.AuthRequest;
import com.example.restful.models.Chat;
import com.example.restful.models.Club;
import com.example.restful.models.CreateMessageDTO;
import com.example.restful.models.CreateNewsDTO;
import com.example.restful.models.Event;
import com.example.restful.models.Group;
import com.example.restful.models.IconImage;
import com.example.restful.models.Image;
import com.example.restful.models.Message;
import com.example.restful.models.News;
import com.example.restful.models.PrivateAccountDTO;
import com.example.restful.models.PublicAccountDTO;
import com.example.restful.models.curriculum.Class;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ServerRepository {
    private static ServerRepository Instance;

    public static ServerRepository getInstance() {
        if (Instance == null) {
            Instance = new ServerRepository();
        }
        return Instance;
    }

    public boolean isAuth() {
        return Handler.getInstance().isAuth();
    }

    public Call<String> login(AuthRequest authRequest) {
        return Handler.getInstance().getApi().getToken(authRequest);
    }

    public Call<PublicAccountDTO> getMyAccount() {
        return Handler.getInstance().getApi().getMyAccount();
    }

    public Call<PublicAccountDTO> getMyAccount(String token) {
        return Handler.getInstance().getApi().getMyAccount("Bearer " + token);
    }

    public Call<List<PrivateAccountDTO>> getAccounts() {
        return Handler.getInstance().getApi().getAccounts();
    }

    public Call<List<Group>> getGroups() {
        return Handler.getInstance().getApi().getGroups();
    }

    public Call<List<Chat>> getChats() { return Handler.getInstance().getApi().getChats(); }

    public Call<Void> sendMessage(CreateMessageDTO message) { return Handler.getInstance().getApi().sendMessage(message); }

    public Call<Void> sendNewChat(Chat chat) { return Handler.getInstance().getApi().sendNewChat(chat); }

    public Call<List<Message>> getMessagesFeed(String date, Long chatId) { return Handler.getInstance().getApi().getMessagesFeed(date,chatId);}

    public Call<Void> createNews(CreateNewsDTO news) { return Handler.getInstance().getApi().createNews(news);}

    public Call<List<News>> getNews() { return Handler.getInstance().getApi().getNews(); }

    public Call<List<News>> getNewsFeed(String date) { return Handler.getInstance().getApi().getNewsFeed(date);}

    public Call<List<Event>> getEvents() { return Handler.getInstance().getApi().getEvents();}

    public Call<Image> getImage(String UUID, String type) { return Handler.getInstance().getApi().getImage(UUID, type);}

    public Call<Void> addIcon(IconImage img) { return Handler.getInstance().getApi().addIcon(img);}

    public Call<Integer> getCountImages(Long id, String type) { return Handler.getInstance().getApi().getCount(id, type);}

    public Call<List<String>> getTopStudentsFaculty() { return Handler.getInstance().getApi().getTopStudentsFaculty();}

    public Call<List<String>> getTopStudentsUniversity() { return Handler.getInstance().getApi().getTopStudentsUniversity();}

    public Call<List<Class>> getClasses() { return Handler.getInstance().getApi().getClasses(); }

    public Call<List<Club>> getClubs() { return Handler.getInstance().getApi().getClubs(); }

    public Call<Void> sendVideo(MultipartBody.Part file, RequestBody metadata) { return Handler.getInstance().getApi().sendVideo(file, metadata);}

    public Call<String> getManifestVideo(String uuid) { return Handler.getInstance().getApi().getVideoManifest(uuid);}
}
