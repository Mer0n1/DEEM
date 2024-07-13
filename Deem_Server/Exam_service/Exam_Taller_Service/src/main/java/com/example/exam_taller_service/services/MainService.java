package com.example.exam_taller_service.services;

import com.example.exam_taller_service.models.List_element;
import com.example.exam_taller_service.repositories.ListAccountsRepository;
import jakarta.el.ELContextEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MainService {

    private ListAccountsRepository repository;

    private List<Long> members; //unique

    @Autowired
    public MainService(ListAccountsRepository repository) {
        this.repository = repository;
        members = new ArrayList<>();
        update();
    }

    public void update() {
        List<List_element> listElements = repository.findAll();
        members.clear();

        for (List_element element : listElements)
            members.add(element.getId_account());
    }

    @Transactional
    public void addMembers(List<Long> newMembers) {
        if (Collections.disjoint(members, newMembers) && newMembers.size() != 0) {
            List<List_element> elements = new ArrayList<>();
            for (Long id : newMembers)
                elements.add(new List_element(id));

            repository.saveAll(elements);
            update();
        }
    }

    @Transactional
    public void deleteMember(List<Long> deleteMembers) {
        List<List_element> elements = new ArrayList<>();
        for (Long id : deleteMembers)
            elements.add(new List_element(id));

        elements = repository.findAllById_account(deleteMembers);
        repository.deleteAll(elements);
        update();
    }

    public List<Long> getMembers() {
        return members;
    }
}
