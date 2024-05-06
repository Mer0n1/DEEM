package com.example.group_service.services;

import com.example.group_service.models.Group;
import com.example.group_service.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository repository;

    @Transactional
    public Group save(Group group) { return repository.save(group); }

    @Transactional
    public void deleteGroup(Group group) { repository.delete(group);}

    @Transactional
    public void deleteGroup(Integer id_group) { repository.deleteById(id_group);}

    @Cacheable("oneGroup")
    public Group getGroup(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Cacheable("allGroups")
    public List<Group> getGroups() { return repository.findAll(); }

    @Cacheable("FacultyGroups")
    public List<Group> getGroupsOfFacultyAndCourse(String faculty, Integer course) { return repository.findAllByFacultyAndCourse(faculty, course);}

}
