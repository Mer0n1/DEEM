package com.example.restful.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class Listener implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // Ваш код обработки запроса

        Response response = chain.proceed(request);

        // Ваш код обработки ответа

        return response;
    }
}
