package com.example.auth_service.repository;


import com.example.auth_service.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    Optional<Account> findByUsername(String username);
    @Query("SELECT u.username FROM Account u WHERE u.id = :id")
    String findUsernameById(@Param("id") int id);
}
