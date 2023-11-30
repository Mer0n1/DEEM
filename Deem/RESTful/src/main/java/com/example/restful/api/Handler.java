package com.example.restful.api;

import com.example.restful.models.Account;
import com.example.restful.models.Group;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Handler {
    private Base base;

    private static Handler instance = null;
    private static String token;

    public Handler() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");

        Gson gson = new GsonBuilder()
                .setDateFormat(dateFormat.toPattern())
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Base.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        base = retrofit.create(Base.class);
    }

    public static synchronized Handler getInstance() {
        if (instance == null) {
            instance = new Handler();
        }
        return instance;
    }

    public static synchronized void setToken(String token) {
        Handler.token = token;
    }

    public static synchronized String getToken() {
        return token;
    }

    public boolean isAuth() {
        if (token == null)
            return false;
        if (token.isEmpty())
            return false;
        else
            return true;
    }

    public Base getApi() {
        return base;
    }


    OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            Request originalRequest = chain.request();
            Request.Builder builder = originalRequest.newBuilder().header("Authorization", "Bearer " + token);
            Request newRequest = builder.build();

            return chain.proceed(newRequest);
        }
    }).build();

}


