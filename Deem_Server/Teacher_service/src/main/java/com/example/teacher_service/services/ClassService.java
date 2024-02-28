package com.example.teacher_service.services;

import com.example.teacher_service.models.Class;
import com.example.teacher_service.repositories.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {

    @Autowired
    private ClassRepository repository;

    @Cacheable("classes")
    public List<com.example.teacher_service.models.Class> getClasses() {
        return repository.findAll();
    }

    @Transactional
    public Class save(Class cl) { return repository.save(cl); }

    @Transactional
    public void delete(Class cl) { //TODO date не сравнивается в БД
        System.out.println(cl.getDate());
        //Optional<Class> m  = repository.findByNameAndDate(cl.getName(), cl.getDate());
        Optional<Class> m = repository.findByDate(cl.getDate());

        if (!m.isEmpty())
            repository.delete(m.get());
    }
}
