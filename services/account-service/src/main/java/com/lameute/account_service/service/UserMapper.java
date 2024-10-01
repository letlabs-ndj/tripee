package com.lameute.account_service.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lameute.account_service.dto.UserRequest;
import com.lameute.account_service.model.User;

/*
 * maps UserRequest to User and vice versa
 */
@Service
public class UserMapper {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    
    public User toUser(UserRequest userRequest){
        User user = new User();
        user.setUsername(userRequest.username());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setPhoneNumber(userRequest.phoneNumber());

        return user;
    }

    public UserRequest fromUser(User user){
        UserRequest userRequest = new UserRequest(
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            user.getPhoneNumber()
        );

        return userRequest;
    }
    
}
