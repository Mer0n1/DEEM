package com.example.restful.api;

import com.example.restful.models.Account;
import com.example.restful.models.AuthRequest;
import com.example.restful.models.Chat;
import com.example.restful.models.Group;
import com.example.restful.models.Message;
import com.example.restful.models.News;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Base {
    String BASE_URL ="http://192.168.1.104:8081/";

    @POST("/auth/login")
    Call<String> getToken(@Body AuthRequest body);
    @GET("/getAuth/getMyAccount")
    Call<Account> getMyAccount();
    @GET("/getAuth/getMyAccount")
    Call<Account> getMyAccount(@Header("Authorization") String token);
    @GET("/getAuth/getAccounts")
    Call<List<Account>> getAccounts();

    @GET("/group/getGroups")
    Call<List<Group>> getGroups();

    @GET("/chat/getChats")
    Call<List<Chat>> getChats();
    @POST("/message/sendMessage")
    Call<Void> sendMessage(@Body Message message);
    @POST("/chat/createChat")
    Call<Void> sendNewChat(@Body Chat chat);
    @GET("/chat/getImageTest")
    Call<ResponseBody> getImage(); //test

    //test
    @Multipart
    @POST("/news/uploadProfile")
    Call<ResponseBody> uploadProfile(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part image); //test!!!!!!
    @GET("/news/test")
    Call<News> getNews();
}
