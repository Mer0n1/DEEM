package com.example.group_service.services;

import com.example.group_service.models.Group;
import com.example.group_service.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository repository;

    public void save(Group group) { repository.save(group); }
    public Group getGroup(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Group> getGroups() { return repository.findAll(); }

    public List<Group> getGroupsOfFaculty(String faculty) { return repository.findAllByFaculty(faculty);}

}
