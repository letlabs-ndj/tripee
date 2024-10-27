package com.lameute.chat_service.repo;

import com.lameute.chat_service.model.ChatMessage;
import com.lameute.chat_service.model.Enums.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepo extends JpaRepository<ChatMessage, Long> {

    Optional<List<ChatMessage>> findByMessageStatus(MessageStatus messageStatus);
}
