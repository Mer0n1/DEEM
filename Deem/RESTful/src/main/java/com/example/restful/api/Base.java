package com.example.restful.api;

import com.example.restful.models.Account;
import com.example.restful.models.AuthRequest;
import com.example.restful.models.Chat;
import com.example.restful.models.Group;
import com.example.restful.models.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

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
}
