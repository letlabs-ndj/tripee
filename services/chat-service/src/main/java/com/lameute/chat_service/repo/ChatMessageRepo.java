package com.lameute.chat_service.repo;

import com.lameute.chat_service.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepo extends JpaRepository<ChatMessage, Long> {
}
