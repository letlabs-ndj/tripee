package com.lameute.chat_service.service;

import com.lameute.chat_service.config.UserDetailsImpl;
import com.lameute.chat_service.model.ChatMessage;
import com.lameute.chat_service.model.Enums.MessageStatus;
import com.lameute.chat_service.repo.ChatMessageRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChatService {

  @Autowired
  private SimpMessageSendingOperations simpMessageSendingOperations;

  @Autowired
  private OnlineOfflineService onlineOfflineService;

  @Autowired
  private ChatMessageRepo chatMessageRepo;

  public void sendMessageToConvId(ChatMessage chatMessage, long receiverId) {
    boolean isTargetOnline = onlineOfflineService.isUserOnline(receiverId);

    if (!isTargetOnline) {
      log.info("{} is not online. Content saved in unseen messages", chatMessage.getReceiverName());
      chatMessage.setMessageStatus(MessageStatus.UNDELIVERED);
      chatMessageRepo.save(chatMessage);
    }
    else {
      log.info("{} is online and message received successfully", chatMessage.getReceiverName());
      simpMessageSendingOperations.convertAndSend("/topic/" + receiverId, chatMessage);
      chatMessage.setMessageStatus(MessageStatus.DELIVERED);
      chatMessageRepo.save(chatMessage);
    }
  }

  public List<ChatMessage> getChatMessageByConvId(String convId){
    return chatMessageRepo.findByConvId(convId)
            .orElseThrow(()->new RuntimeException("No messages for this convId"));
  }

  public UserDetailsImpl getUser() {
    Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return (UserDetailsImpl) object;
  }
}
