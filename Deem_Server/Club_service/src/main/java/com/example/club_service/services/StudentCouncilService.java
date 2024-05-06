package com.example.club_service.services;

import com.example.club_service.models.Account;
import com.example.club_service.models.StudentCouncil;
import com.example.club_service.repositories.StudCouncilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentCouncilService {

    @Autowired
    private StudCouncilRepository repository;

    @Transactional
    public void save(StudentCouncil studentCouncil) { repository.save(studentCouncil); }

    @Transactional
    public void delete(StudentCouncil studentCouncil) { repository.delete(studentCouncil); }

    public StudentCouncil getStudentCouncil(Long id_account) throws Exception {
        return repository.findByIdAccount(id_account).orElseThrow(
                ()->new Exception("Такой участник не состоит в студсовете"));
    }

    public void addNewMember(Account account) {
        StudentCouncil studentCouncil = new StudentCouncil();
        studentCouncil.setIdAccount(account.getId());
        studentCouncil.setType("member");

        save(studentCouncil);
    }

}
