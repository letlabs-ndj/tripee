package com.lameute.chat_service.model;

import com.lameute.chat_service.model.Enums.MessageStatus;
import com.lameute.chat_service.model.Enums.MessageType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat_messages")
public class ChatMessage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long messageId;
    private long senderId;
    private long receiverId;
    private String convId;
    private String senderName;
    private String receiverName;
    private String content;
    private String sendingTime;
    private String sendingDate;
    private MessageType messageType;
    private MessageStatus messageStatus;
}
