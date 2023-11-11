package com.example.restful.api;

import com.example.restful.api.websocket.PushClient;
import com.example.restful.models.Account;
import com.example.restful.models.AuthRequest;
import com.example.restful.models.Chat;
import com.example.restful.models.Group;
import com.example.restful.models.Message;
import com.example.restful.models.News;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class APIManager {

    private static APIManager manager;
    private static PushClient pushClient;

    public List<Account> listAccounts;
    public List<Group> listGroups;
    public List<Chat> listChats;
    public List<News> listNews;

    public Account myAccount;

    private String jwtKey;

    private APIManager() {
    }

    public static APIManager getManager() {
        if (manager == null) {
            manager = new APIManager();
            pushClient = new PushClient();
        }
        return manager;
    }

    public boolean isAuth() {
        return Handler.getInstance().isAuth();
    }

    public void auth(AuthRequest authRequest) {

        final Call<String> str = Repository.getInstance().login(authRequest);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Response<String> stri = null;
                try {
                    stri = str.execute();
                    Handler.setToken(stri.body());
                } catch (IOException e) {
                    e.printStackTrace();
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
System.out.println("000000 " + jwtKey);
        pushClient.auth(jwtKey);
    }

    public void getMyAccount() {
        Call<Account> str = Repository.getInstance().getMyAccount(Handler.getToken());
        Callback<Account> cl = new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.body() != null)
                System.out.println(response.body().getSurname());
                myAccount = response.body();
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {

            }
        };

        str.enqueue(cl);
    }

    public List<Account> getAccounts() {
        return new ArrayList<>();
    }

    public List<Group> getGroups() {
        return new ArrayList<>();
    }

    public Account getAccount(String username) {
        return new Account();
    }

    public void UpdateData() {

        final Call<Account> str = Repository.getInstance().getMyAccount();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Response<Account> stri = null;
                try {
                    stri = str.execute();
                    myAccount = stri.body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---- " + myAccount.getUsername());


        //Update accounts
        Repository.getInstance().getAccounts()
                .enqueue(new Callback<List<Account>>() {
                    @Override
                    public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                        listAccounts = response.body();
                    }

                    @Override
                    public void onFailure(Call<List<Account>> call, Throwable t) {

                    }
                }
        );


        //
        Repository.getInstance().getGroups().enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                listGroups = response.body();
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                System.err.println("Failure ошибка групп " + t.getMessage());
            }
        });

        //
        Repository.getInstance().getChats().enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                listChats = response.body();
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                System.err.println("Failure ошибка чатов " + t.getMessage());
            }
        });


        Repository.getInstance().getNews(myAccount.getGroup().getFaculty()).enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                listNews = response.body();
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {

            }
        });

    }

    public void sendMessage(Message message) {
        Repository.getInstance().sendMessage(message).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void sendNewChat(Chat chat) {
        Repository.getInstance().sendNewChat(chat).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

}


