package com.example.restful.api;

import com.example.restful.models.Account;
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
import com.example.restful.models.MessageImage;
import com.example.restful.models.News;
import com.example.restful.models.NewsImage;
import com.example.restful.models.PrivateAccountDTO;
import com.example.restful.models.PublicAccountDTO;
import com.example.restful.models.curriculum.Class;

import java.util.Date;
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
import retrofit2.http.Path;
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
    Call<Long> sendMessage(@Body CreateMessageDTO message);
    @POST("/chat/createChat")
    Call<Void> sendNewChat(@Body Chat chat);
    @GET("/message/getMessagesFeed")
    Call<List<Message>> getMessagesFeed(@Query("date") String date, @Query("chatId") Long chatId);

    //news
    @GET("/news/getNews")
    Call<List<News>> getNews();
    @POST("/news/createNews")
    Call<Long> createNews(@Body CreateNewsDTO news);
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

    //Video
    @Multipart
    @POST("/video/upload")
    Call<Void> sendVideo(
            @Part MultipartBody.Part file,
            @Part("metadata") RequestBody metadata
    );
    @GET("/video/getManifest/{uuid}") //запрос на получение манифеста видео
    Call<String> getVideoManifest(@Path("uuid") String uuid);
}
