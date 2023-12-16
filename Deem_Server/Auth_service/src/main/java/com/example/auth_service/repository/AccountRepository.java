package com.example.auth_service.repository;


import com.example.auth_service.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    Optional<Account> findByUsername(String username);
    Optional<Account> findById(Long id);
    List<Account> findAllByIdIn(List<Long> chatIds);
    @Query("SELECT u.username FROM Account u WHERE u.id = :id")
    String findUsernameById(@Param("id") int id);

    void deleteById(Long id);

    @Query("SELECT u.id FROM Account u WHERE u.group_id= :idGroup")
    List<Long> findUsersOfGroup(@Param("idGroup") Long idGroup);
}
