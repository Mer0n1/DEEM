package com.example.messenger_service.repositories;

import com.example.messenger_service.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    List<Chat> findAllByIdIn(List<Long> chatIds);
    Chat findChatById(int id);

}


