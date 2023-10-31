package com.example.group_service.controllers;

import com.example.group_service.models.Group;
import com.example.group_service.services.GroupService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/test")
    public String test(Principal principal, Authentication authentication) {
        System.out.println("------");
        System.out.println(principal);
        System.out.println(principal.getName());

        return "test";
    }

    @GetMapping("/getGroup")
    public Group getGroup(@RequestParam("id") int id) {
        return groupService.getGroup(id);
    }

    @GetMapping("/getGroups")
    public List<Group> getGroups() {
        return groupService.getGroups();
    }

    @GetMapping("/getTableOfTopGroups")
    public List<Group> getGroupsTops() {
        return groupService.sort(groupService.getGroups());
    }
}
