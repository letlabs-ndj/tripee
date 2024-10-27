package com.lameute.chat_service.service;

import com.lameute.chat_service.config.UserDetailsImpl;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import com.lameute.chat_service.model.ChatMessage;
import com.lameute.chat_service.model.Enums.MessageStatus;
import com.lameute.chat_service.repo.ChatMessageRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OnlineOfflineService {
  private final Set<Long> onlineUsers;
  private final Map<Long, Set<String>> userSubscribed;
  private final ChatMessageRepo chatMessageRepo;
  private final SimpMessageSendingOperations simpMessageSendingOperations;

  @Autowired
  public OnlineOfflineService(ChatMessageRepo chatMessageRepo, SimpMessageSendingOperations simpMessageSendingOperations) {
      this.chatMessageRepo = chatMessageRepo;
      this.simpMessageSendingOperations = simpMessageSendingOperations;
      this.onlineUsers = new ConcurrentSkipListSet<>();
      this.userSubscribed = new ConcurrentHashMap<>();
  }

  public void addOnlineUser(Principal user) {
    if (user == null) return;
    UserDetailsImpl userDetails = getUserDetails(user);
    log.info("{} is online", userDetails.getUsername());
    log.info("{} is online", userDetails.getId());
    onlineUsers.add(userDetails.getId());
  }

  public void removeOnlineUser(Principal user) {
    if (user != null) {
      UserDetailsImpl userDetails = getUserDetails(user);
      log.info("{} went offline", userDetails.getUsername());
      onlineUsers.remove(userDetails.getId());
      userSubscribed.remove(userDetails.getId());
    }
  }

  public boolean isUserOnline(long userId) {
    return onlineUsers.contains(userId);
  }

  private UserDetailsImpl getUserDetails(Principal principal) {
    UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) principal;
    Object object = user.getPrincipal();
    return (UserDetailsImpl) object;
  }

  public void addUserSubscribed(Principal user, String subscribedChannel) {
    UserDetailsImpl userDetails = getUserDetails(user);
    if (!isUserSubscribed(userDetails.getId(), subscribedChannel)){
      System.out.println("not yet subscribed");
      log.info("{} subscribed to {}", userDetails.getUsername(), subscribedChannel);
      Set<String> subscriptions = userSubscribed.getOrDefault(userDetails.getId(), new HashSet<>());
      subscriptions.add(subscribedChannel);
      userSubscribed.put(userDetails.getId(), subscriptions);

      List<ChatMessage> chatMessages = chatMessageRepo.findByMessageStatus(MessageStatus.UNDELIVERED)
              .orElseThrow(()->new RuntimeException("No un received messages"));

      for (ChatMessage chatMessage : chatMessages){
        simpMessageSendingOperations.convertAndSend("/topic/" + userDetails.getId(), chatMessage);
        chatMessage.setMessageStatus(MessageStatus.DELIVERED);
        chatMessageRepo.save(chatMessage);
      }
    }
  }

  public void removeUserSubscribed(Principal user, String subscribedChannel) {
    UserDetailsImpl userDetails = getUserDetails(user);
    log.info("unsubscription! {} unsubscribed {}", userDetails.getUsername(), subscribedChannel);
    Set<String> subscriptions = userSubscribed.getOrDefault(userDetails.getId(), new HashSet<>());
    subscriptions.remove(subscribedChannel);
    userSubscribed.put(userDetails.getId(), subscriptions);
  }

  public boolean isUserSubscribed(long userId, String subscription) {
    Set<String> subscriptions = userSubscribed.getOrDefault(userId, new HashSet<>());
    return subscriptions.contains(subscription);
  }
}
