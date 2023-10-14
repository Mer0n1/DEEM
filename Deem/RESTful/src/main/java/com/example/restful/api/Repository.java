package com.example.restful.api;

import com.example.restful.models.Account;
import com.example.restful.models.AuthRequest;
import com.example.restful.models.Chat;
import com.example.restful.models.Group;
import com.example.restful.models.Message;

import java.util.List;

import retrofit2.Call;

class Repository {
    private static Repository Instance;

    //    private ArrayList<User> dataSet =new ArrayList<User>();
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
}
