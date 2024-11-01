package com.lameute.chat_service.repo;

import com.lameute.chat_service.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepo extends JpaRepository<Conversation, Long> {
    Optional<List<Conversation>> findByUserId(long userId);

    boolean existsByConvId(String convId);
}
