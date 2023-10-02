package com.example.deem.layer_server;

import com.example.restful.models.Account;
import com.example.restful.models.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, отвечающий за хранение данных и их актуальность.
 * Делает запросы через RESTful сервис к серверу и получает актуальные данные.
 */
public class DataCash {
    private static List<Account> accounts;
    private static List<Group> groups;
    private static Account myAccount;

    private DataCash() {}
    static {
        myAccount = new Account();
        accounts = new ArrayList<>();
        groups   = new ArrayList<>();
    }

    public static List<Account> getAccounts() {
        return accounts;
    }

    public static Account getAccount(String name) {
        return accounts.stream().filter(x->x.getUsername().equals(name)).findAny().orElse(null);
    }

    public static List<Group> getGroups() {
        return groups;
    }

    public static Account getMyAccount() {
        return myAccount;
    }

    public static void setMyAccount(Account myAccount) {
        DataCash.myAccount = myAccount;
    }
    public static void initAccount(String username) { myAccount = MainAPI.getAccount(username);}

    public static void UpdateData() {
        accounts = MainAPI.getAccounts();
        groups   = MainAPI.getGroups();
        myAccount = accounts.stream().filter(x->x.getUsername()
                .equals(myAccount.getUsername())).findAny().orElse(null);
    }

}
