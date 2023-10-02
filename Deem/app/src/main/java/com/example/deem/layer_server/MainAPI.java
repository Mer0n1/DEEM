package com.example.deem.layer_server;

import com.example.deem.layer_server.utils.Validator;
import com.example.restful.api.AccountsAPI;
import com.example.restful.api.GroupsAPI;
import com.example.restful.models.Account;
import com.example.restful.models.AuthRequest;
import com.example.restful.models.Group;

import java.util.List;

/**
 * Класс, отвечающий за доступ к RESTful сервису.
 * Это некоторая прослойка между Android и RESTful сервисом
 *
 * MainAPI заботится о том, чтобы создать модель, проверить модель на валидность,
 * отправить, а также принять ее.
 */
public class MainAPI {
    private static AccountsAPI accountsAPI;
    private static GroupsAPI groupsAPI;
    private static Validator validator;

    static {
        validator = new Validator();
        accountsAPI = new AccountsAPI();
        groupsAPI = new GroupsAPI();
    }


    public static List<Account> getAccounts() { return accountsAPI.getAccounts(); }

    public static List<Group> getGroups() { return groupsAPI.getGroups(); }

    public static Account getAccount(String username) { return accountsAPI.getAccount(username);}

    public static Account getMyAccount() { return accountsAPI.getMyAccount();}

    /**
     * Аутентификация с последующей обработкой ошибок.
     * Исключения должны быть обработаны и показаны в поле ошибок в Android окне
     */
    public static boolean authentication(AuthRequest form) {
        if (!validator.CheckAccount(form))
            return false;

        return accountsAPI.authentication(form);
    }

    public static void test() {
        groupsAPI.test();
    }
}
