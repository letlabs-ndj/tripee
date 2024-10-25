package com.lameute.chat_service.utils;

import java.security.Principal;

import com.lameute.chat_service.config.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

  public UserDetailsImpl getUserDetails(Principal principal) {
    UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) principal;
    Object object = user.getPrincipal();
    return (UserDetailsImpl) object;
  }

  public UserDetailsImpl getUser() {
    Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return (UserDetailsImpl) object;
  }
}
