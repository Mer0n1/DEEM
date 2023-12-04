package com.example.group_service.controllers;

import com.example.group_service.models.Group;
import com.example.group_service.models.LocationStudent;
import com.example.group_service.services.GroupService;
import com.example.group_service.services.RestTemplateClient;
import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private RestTemplateClient restTemplateClient;

    @GetMapping("/getGroup")
    public Group getGroup(@RequestParam("id") Long id) {
        Group group =groupService.getGroup(id);
        group.setUsers(restTemplateClient.getListIdUsersOfGroup(group.getId()));
        return group;
    }

    @GetMapping("/getGroups")
    public List<Group> getGroups(Principal principal) {
        List<Group> groups = groupService.getGroups();
        for (Group group : groups)
            group.setUsers(restTemplateClient.getListIdUsersOfGroup(group.getId()));

        return groups; //TODO: узнаем факультет
    }

    @GetMapping("/getTableOfTopGroups")
    public List<Group> getGroupsTops() {
        return groupService.sort(groupService.getGroups());
    }

    @PreAuthorize("hasRole('HIGH')")
    @PostMapping("/createGroup")
    public void createGroup(@RequestBody @Valid Group group,
                            BindingResult bindingResult) {
        System.out.println("createGroup");

        if (bindingResult.hasErrors())
            return;

        groupService.save(group);
    }

    @GetMapping("/getLocationStudent")
    public LocationStudent getLocationStudent(@RequestParam("id") Long idGroup) {
        Group group = groupService.getGroup(idGroup);

        return new LocationStudent(0l, group.getFaculty(), group.getCourse());
    }

}
