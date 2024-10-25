package com.lameute.chat_service.config;

import com.lameute.chat_service.service.UserClient;
import com.lameute.chat_service.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class WebSocketTokenFilter implements ChannelInterceptor {
  @Autowired
  private JWTUtils jwtUtils;

  @Autowired
  private UserClient userClient;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    final StompHeaderAccessor accessor =
        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
    if (StompCommand.CONNECT == accessor.getCommand()) {

      String jwt = jwtUtils.parseJwt(accessor);
      System.out.println(jwt);
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        String userEmail = jwtUtils.extractUserEmail(jwt);

        var user = userClient.getUserByEmail(userEmail);

        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(user.id())
                .username(user.email())
                .password(user.password())
                .build();

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        accessor.setUser(authentication);
      }
    }
    return message;
  }
}
