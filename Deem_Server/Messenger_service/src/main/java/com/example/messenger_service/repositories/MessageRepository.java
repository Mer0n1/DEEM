package com.example.messenger_service.repositories;

import com.example.messenger_service.models.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    Message findById(int id);

    @Query("SELECT n FROM Message n WHERE n.date < ?1 AND n.chat.id=?2")
    List<Message> findAllByDate(Date date, Long chatId, Pageable pageable);
}



