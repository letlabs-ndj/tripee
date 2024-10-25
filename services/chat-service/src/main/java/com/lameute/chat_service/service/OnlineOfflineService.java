package com.lameute.chat_service.service;

import com.lameute.chat_service.config.UserDetailsImpl;
import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
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
  private final SimpMessageSendingOperations simpMessageSendingOperations;

  @Autowired
  public OnlineOfflineService(SimpMessageSendingOperations simpMessageSendingOperations) {
    this.onlineUsers = new ConcurrentSkipListSet<>();
    this.userSubscribed = new ConcurrentHashMap<>();
    this.simpMessageSendingOperations = simpMessageSendingOperations;
  }

  public void addOnlineUser(Principal user) {
    if (user == null) return;
    UserDetailsImpl userDetails = getUserDetails(user);
    log.info("{} is online", userDetails.getUsername());
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

//  public List<UserResponse> getOnlineUsers() {
//    return userRepository.findAllById(onlineUsers).stream()
//        .map(
//            userEntity ->
//                new UserResponse(
//                    userEntity.getId(), userEntity.getUsername(), userEntity.getEmail()))
//        .toList();
//  }

  public void addUserSubscribed(Principal user, String subscribedChannel) {
    UserDetailsImpl userDetails = getUserDetails(user);
    log.info("{} subscribed to {}", userDetails.getUsername(), subscribedChannel);
    Set<String> subscriptions = userSubscribed.getOrDefault(userDetails.getId(), new HashSet<>());
    subscriptions.add(subscribedChannel);
    userSubscribed.put(userDetails.getId(), subscriptions);
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

//  public Map<String, Set<String>> getUserSubscribed() {
//    Map<String, Set<String>> result = new HashMap<>();
//    List<UserEntity> users = userRepository.findAllById(userSubscribed.keySet());
//    users.forEach(user -> result.put(user.getUsername(), userSubscribed.get(user.getId())));
//    return result;
//  }

//  public void notifySender(
//      long senderId,
//      List<ConversationEntity> entities,
//      MessageDeliveryStatusEnum messageDeliveryStatusEnum) {
//    if (!isUserOnline(senderId)) {
//      log.info(
//          "{} is not online. cannot inform the socket. will persist in database",
//          senderId.toString());
//      return;
//    }
//    List<MessageDeliveryStatusUpdate> messageDeliveryStatusUpdates =
//        entities.stream()
//            .map(
//                e ->
//                    MessageDeliveryStatusUpdate.builder()
//                        .id(e.getId())
//                        .messageDeliveryStatusEnum(messageDeliveryStatusEnum)
//                        .content(e.getContent())
//                        .build())
//            .toList();
//    for (ConversationEntity entity : entities) {
//      simpMessageSendingOperations.convertAndSend(
//          "/topic/" + senderId,
//          ChatMessage.builder()
//              .id(entity.getId())
//              .messageDeliveryStatusUpdates(messageDeliveryStatusUpdates)
//              .messageType(MessageType.MESSAGE_DELIVERY_UPDATE)
//              .content(entity.getContent())
//              .build());
//    }
//  }
}
