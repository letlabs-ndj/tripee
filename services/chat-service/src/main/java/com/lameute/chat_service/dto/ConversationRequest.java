package com.lameute.chat_service.dto;

public record ConversationRequest(
        String convId,
        long userId,
        String username,
        long interlocutorId,
        String interlocutorName
) {
}
