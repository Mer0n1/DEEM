package com.example.restful.api;

import com.example.restful.models.Account;
import com.example.restful.models.AuthRequest;
import com.example.restful.models.Chat;
import com.example.restful.models.DataImage;
import com.example.restful.models.Event;
import com.example.restful.models.Group;
import com.example.restful.models.IconImage;
import com.example.restful.models.Image;
import com.example.restful.models.Message;
import com.example.restful.models.MessageImage;
import com.example.restful.models.News;
import com.example.restful.models.NewsImage;

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
import retrofit2.http.Query;

public interface Base {
    String BASE_URL ="http://192.168.1.104:8081/";

    //auth
    @POST("/auth/login")
    Call<String> getToken(@Body AuthRequest body);
    @GET("/getAuth/getMyAccount")
    Call<Account> getMyAccount();
    @GET("/getAuth/getMyAccount")
    Call<Account> getMyAccount(@Header("Authorization") String token);
    @GET("/getAuth/getAccounts")
    Call<List<Account>> getAccounts();

    //groups
    @GET("/group/getGroups")
    Call<List<Group>> getGroups();

    //chats
    @GET("/chat/getChats")
    Call<List<Chat>> getChats();
    @POST("/message/sendMessage")
    Call<Void> sendMessage(@Body Message message);
    @POST("/chat/createChat")
    Call<Void> sendNewChat(@Body Chat chat);
    /*@GET("/chat/getImageTest")
    Call<ResponseBody> getImage(); //test
    */
    //test
    /*@Multipart
    @POST("/news/uploadProfile")
    Call<ResponseBody> uploadProfile(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part image);*/

    //news
    @GET("/news/getNews")
    Call<List<News>> getNews(@Query("faculty") String faculty);
    @POST("/news/createNews")
    Call<Void> createNews(@Body News news);

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
    @POST("/image/addImagesMessage")
    Call<Void> addImagesMessage(@Body List<MessageImage> imgs);
    @POST("/image/addImagesNews")
    Call<Void> addImagesNews(@Body List<NewsImage> imgs);
}
