package com.example.messenger_service.repositories;

import com.example.messenger_service.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    //List<Chat>
    List<Chat> findAllByIdIn(List<Long> chatIds);
    Chat findChatById(int id);
}


