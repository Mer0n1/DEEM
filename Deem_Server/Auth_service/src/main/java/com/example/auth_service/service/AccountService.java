package com.example.auth_service.service;

import com.example.auth_service.models.Account;
import com.example.auth_service.models.ChangeRoleForm;
import com.example.auth_service.models.ClubForm;
import com.example.auth_service.models.ListLong;
import com.example.auth_service.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;

    @Transactional
    public Account save(Account account) { return repository.save(account); }
    @Transactional
    public void delete(Long idAccount) { repository.deleteById(idAccount); }

    @Cacheable("account_name")
    public Account getAccount(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    @Cacheable("account_id")
    public Account getAccount(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new Exception("Такого аккаунта не существует"));
    }

    @Cacheable("accounts")
    public List<Account> getAccounts() {
        return repository.findAll();
    }

    @Cacheable("users_of_group")
    public List<Long> getUsersOfGroup(Long idGroup) { return repository.findUsersOfGroup(idGroup);}

    @Cacheable("topUsers")
    public List<String> getTopUniversity() {
        List<Account> accounts = repository.findTopUniversity();
        List<String> list = new ArrayList<>();
        for (Account account : accounts)
            list.add(account.getName() + " " + account.getSurname());
        return list;
    }


    @Transactional
    public void sendScore(Long idAccount, int score) throws Exception {
        Account account = repository.findById(idAccount).orElseThrow(()->new Exception("Пользователь не найден"));
        account.addScore(score);
    }

    @Transactional
    public void transferAccount(Long idStudent, Long idGroup) throws Exception {
        Account account = repository.findById(idStudent).orElseThrow(
                ()->new Exception("Пользователь не найден"));
        if (account.getGroup_id() == idGroup)
            throw new Exception("Студент уже состоит в этой группе");
        account.setGroup_id(idGroup);
    }

    @Transactional
    public void addStudentToClub(ClubForm form) throws Exception {

        //узнаем состоит ли студент в клубе
        Account account = repository.findById(form.getId_student())
                .orElseThrow(() -> new Exception("Такого аккаунта не существует"));

        if (account.getId_club() != null)
            throw new Exception("Студент уже состоит в клубе");

        //если у студента нет клуба, то назначаем ему его
        account.setId_club(form.getId_club());
        repository.save(account);
    }

    @Transactional
    public void expelStudentClub(ClubForm form) throws Exception {

        //узнаем состоит ли студент в клубе
        Account account = repository.findById(form.getId_student())
                .orElseThrow(() -> new Exception("Такого аккаунта не существует"));

        if (account.getId_club() == null || account.getId_club() != form.getId_club())
            throw new Exception("Студент не состоит в клубе");

        //если у студента нет клуба, то назначаем ему его
        account.setId_club(null);
        repository.save(account);
    }

    @Transactional
    public void changeRole(ChangeRoleForm form) throws Exception {
        Account account = repository.findById(form.getId_student())
                .orElseThrow(() -> new Exception("Такого аккаунта не существует"));

        account.setRole("ROLE_" + form.getNewRole());
    }

    public List<Integer> getListAverageValue(List<ListLong> list) {
        List<Integer> result_list = new ArrayList<>();

        for (ListLong listInteger : list) {
            List<Account> accountList = repository.findAllByIdIn(listInteger.list);

            Integer aver = 0;

            for (Account account : accountList)
                aver += account.getScore();
            if (accountList.size() != 0)
                aver /= accountList.size();

            result_list.add(aver);
        }
        return result_list;
    }

    /** Если аккаунт принадлежит чужой группе то скрываем баллы.
     *  Баллы видны только своим участникам группы */
    public void hideScoreOtherGroup(Long idGroup, List<Account> accounts) {
        for (Account account : accounts)
            if (!account.getGroup_id().equals(idGroup))
                account.setScore(null);
    }
}
