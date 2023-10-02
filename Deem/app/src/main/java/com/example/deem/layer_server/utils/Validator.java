package com.example.deem.layer_server.utils;

import com.example.restful.models.AuthRequest;
import com.example.restful.models.Group;

/**
 * Класс, проверяющий валидность моделей
 */
public class Validator {


    public boolean CheckAccount(AuthRequest form) {
        if (form.getUsername().isEmpty() ||
            form.getPassword().isEmpty())
            return false;
        return true;
    }

    public boolean CheckGroup(Group group) {
        if (group == null)
            return false;
        if (group.getName().isEmpty())
            return false;
        return true;
    }

}
