package com.lameute.chat_service.service;

import com.lameute.chat_service.config.UserDetailsImpl;
import com.lameute.chat_service.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatService {

  private final SimpMessageSendingOperations simpMessageSendingOperations;

//  private final ConversationRepository conversationRepository;

  private final OnlineOfflineService onlineOfflineService;

  @Autowired
  public ChatService(
      SimpMessageSendingOperations simpMessageSendingOperations,
      OnlineOfflineService onlineOfflineService) {
    this.simpMessageSendingOperations = simpMessageSendingOperations;
    this.onlineOfflineService = onlineOfflineService;
  }

  public void sendMessageToConvId(
          ChatMessage chatMessage, long receiverId) {
//    long fromUserId = chatMessage.getSenderId();
//    long toUserId = chatMessage.getReceiverId();
    boolean isTargetOnline = onlineOfflineService.isUserOnline(receiverId);
    boolean isTargetSubscribed =
        onlineOfflineService.isUserSubscribed(receiverId, "/topic/" + receiverId);

    if (!isTargetOnline) {
      log.info(
          "{} is not online. Content saved in unseen messages", chatMessage.getReceiverName());
//      conversationEntityBuilder.deliveryStatus(MessageDeliveryStatusEnum.NOT_DELIVERED.toString());
//      chatMessage.setMessageDeliveryStatusEnum(MessageDeliveryStatusEnum.NOT_DELIVERED);

    } else if (!isTargetSubscribed) {
      log.info(
          "{} is online but not subscribed. sending to their private subscription",
          chatMessage.getReceiverName());
//      conversationEntityBuilder.deliveryStatus(MessageDeliveryStatusEnum.DELIVERED.toString());
//      chatMessage.setMessageDeliveryStatusEnum(MessageDeliveryStatusEnum.DELIVERED);
      simpMessageSendingOperations.convertAndSend("/topic/" + receiverId, chatMessage);

    } else {
      log.info(
              "{} is online and message received successfully",
              chatMessage.getReceiverName());
//      conversationEntityBuilder.deliveryStatus(MessageDeliveryStatusEnum.SEEN.toString());
//      chatMessage.setMessageDeliveryStatusEnum(MessageDeliveryStatusEnum.SEEN);
    }
//    conversationRepository.save(conversationEntityBuilder.build());
    simpMessageSendingOperations.convertAndSend("/topic/" + receiverId, chatMessage);
  }

  public UserDetailsImpl getUser() {
    Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return (UserDetailsImpl) object;
  }
}
