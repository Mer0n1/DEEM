package com.example.restful.api;


import com.example.restful.Json.JsonConverter;
import com.example.restful.models.Account;
import com.example.restful.models.AuthRequest;

import java.util.List;


public class AccountsAPI {

    private final String LOGIN_URL;
    private final String GET_ACCOUNTS_URL;
    private final String GET_ACCOUNT;
    private final String GET_MY_ACCOUNT;

    public AccountsAPI() {
        String mainIP = "http://192.168.1.104:8081"; //
        LOGIN_URL = mainIP + "/auth/login";
        GET_ACCOUNTS_URL = mainIP + "/getAuth/getAccounts";
        GET_ACCOUNT = mainIP + "/getAuth/getAccount";
        GET_MY_ACCOUNT = mainIP + "/getAuth/getMyAccount";
    }

    public boolean authentication(AuthRequest form) {
        return APIServer.login(LOGIN_URL, form);
    }

    public List<Account> getAccounts() {
        String response = APIServer.getFromServer(GET_ACCOUNTS_URL);
        System.out.println("respo " + response);
        return JsonConverter.getObjects(response, Account.class);
    }

    public Account getAccount(String name) {
        String body = "?name=" + name;

        String response = APIServer.getFromServer(GET_ACCOUNT + body);

        return JsonConverter.getObject(response, Account.class);
    }

    public Account getMyAccount() {
        String response = APIServer.getFromServer(GET_MY_ACCOUNT);
        return JsonConverter.getObject(response, Account.class);
    }

    /*public boolean register(AuthRequest form) {
        String json = JsonConverter.getJson(form);
        String response = APIServer.postToServer(REGISTER, APIServer.TypeContent.json, json);

        return APIServer.itsOk(response);
    }*/

}
