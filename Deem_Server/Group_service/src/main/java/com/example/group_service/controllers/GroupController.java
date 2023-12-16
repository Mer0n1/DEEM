package com.example.group_service.controllers;

import com.example.group_service.config.PersonDetails;
import com.example.group_service.models.Group;
import com.example.group_service.models.ListLong;
import com.example.group_service.models.LocationStudent;
import com.example.group_service.services.GroupService;
import com.example.group_service.services.RestTemplateClient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

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
    public List<Group> getGroups(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        //Получим список групп для текущего факультета заявителя
        List<Group> groups = groupService.getGroupsOfFaculty(((PersonDetails)userDetails).getFaculty());
        //Получим список учащихся групп
        for (Group group : groups)
            group.setUsers(restTemplateClient.getListIdUsersOfGroup(group.getId()));
        //Вычислим средние значения каждой группы
        List<ListLong> list_id = new ArrayList<>();
        for (int j = 0; j < groups.size(); j++) {
            list_id.add(new ListLong());
            list_id.get(j).list = groups.get(j).getUsers();
        }

        List<Long> list_score = restTemplateClient.getListTopGroups(list_id);
        groups.sort(Comparator.comparingInt(list_score::indexOf));

        if (list_score.size() != 0)
        for (int j = 0; j < groups.size(); j++)
            groups.get(j).setScore(list_score.get(j).intValue());

        return groups;
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
