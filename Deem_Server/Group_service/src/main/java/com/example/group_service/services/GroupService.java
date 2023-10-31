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

    public Group getGroup(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Group> getGroups() { return repository.findAll(); }

    public List<Group> sort(List<Group> groups) {
        List<Group> tops = new ArrayList<>();
        groups.stream().sorted(Comparator.comparing(o -> o.getScore()));

        int size = Math.min(groups.size(), 10);
        for (int j = 0; j < size; j++)
            tops.add(groups.get(j));
        return tops;
    }
}
