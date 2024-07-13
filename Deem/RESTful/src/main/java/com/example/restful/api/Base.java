package com.example.restful.api;

import com.example.restful.models.Account;
import com.example.restful.models.AuthRequest;
import com.example.restful.models.Chat;
import com.example.restful.models.Club;
import com.example.restful.models.Event;
import com.example.restful.models.Group;
import com.example.restful.models.IconImage;
import com.example.restful.models.Image;
import com.example.restful.models.Message;
import com.example.restful.models.MessageImage;
import com.example.restful.models.News;
import com.example.restful.models.NewsImage;
import com.example.restful.models.PrivateAccountDTO;
import com.example.restful.models.PublicAccountDTO;
import com.example.restful.models.curriculum.Class;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Base {
    String BASE_URL ="http://192.168.0.103:8081/";

    //auth
    @POST("/auth/login")
    Call<String> getToken(@Body AuthRequest body);
    @GET("/getAuth/getMyAccount")
    Call<PublicAccountDTO> getMyAccount();
    @GET("/getAuth/getMyAccount")
    Call<PublicAccountDTO> getMyAccount(@Header("Authorization") String token);
    @GET("/getAuth/getAccounts")
    Call<List<PrivateAccountDTO>> getAccounts();
    @GET("/getAuth/getTopStudentsFaculty")
    Call<List<String>> getTopStudentsFaculty();
    @GET("/getAuth/getTopStudentsUniversity")
    Call<List<String>> getTopStudentsUniversity();

    //groups
    @GET("/group/getAllGroups")
    Call<List<Group>> getGroups();

    //chats
    @GET("/chat/getChats")
    Call<List<Chat>> getChats();
    @POST("/message/sendMessage")
    Call<Void> sendMessage(@Body Message message);
    @POST("/chat/createChat")
    Call<Void> sendNewChat(@Body Chat chat);

    //news
    @GET("/news/getNews")
    Call<List<News>> getNews();
    @POST("/news/createNews")
    Call<Void> createNews(@Body News news);
    @GET("/news/getNewsFeed")
    Call<List<News>> getNewsFeed(@Query("date") String date);

    //events or exams
    @GET("/event/getEvents")
    Call<List<Event>> getEvents();

    //image
    @GET("/image/getImage")
    Call<Image> getImage(@Query("UUID") String UUID, @Query("type") String type);
    @GET("/image/getCount")
    Call<Integer> getCount(@Query("id") Long id, @Query("type") String type);
    @POST("/image/addImageIcon")
    Call<Void> addIcon(@Body IconImage image);

    //teacher
    @GET("/curriculum/getTwoWeek")
    Call<List<Class>> getClasses();

    //club
    @GET("/club/getClubs")
    Call<List<Club>> getClubs();
}
