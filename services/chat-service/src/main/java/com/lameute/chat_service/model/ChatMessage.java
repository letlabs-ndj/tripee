package com.lameute.chat_service.model;

import lombok.*;

import java.nio.file.FileStore;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a chat message in the chat application.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    private long messageId;
    private long senderId;
    private long receiverId;
    private String senderName;
    private String receiverName;
    private String content;
    private LocalTime sendingTime;
    private LocalDate sendingDate;
    private MessageType messageType;
    private UserConnection userConnection;

    /**
     * Enum representing the type of the chat message.
     */
    public enum MessageType {
        CHAT, LEAVE, JOIN
    }

}
