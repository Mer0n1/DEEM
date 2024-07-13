package com.example.exam_taller_service.repositories;

import com.example.exam_taller_service.models.List_element;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListAccountsRepository extends JpaRepository<List_element,Integer> {
    @Query("SELECT le FROM List_element le WHERE le.id_account IN :idAccounts")
    List<List_element> findAllById_account(@Param("idAccounts") List<Long> id_accounts);
}
