package com.example.restful.api;

import com.example.restful.Json.JsonConverter;
import com.example.restful.models.Group;

import java.util.List;

public class GroupsAPI {

    private final String GET_GROUPS;

    public GroupsAPI() {
        String mainIP = "http://192.168.1.104:8081";
        GET_GROUPS = mainIP + "/group/getGroups";
    }


    public List<Group> getGroups() {
        String response = APIServer.getFromServer(GET_GROUPS);
        System.out.println("response " + response);
        return JsonConverter.getObjects(response, Group.class);
    }

    public void test() {
        System.out.println("+++++++++ " + APIServer.getFromServer(GET_GROUPS));

    }


}
