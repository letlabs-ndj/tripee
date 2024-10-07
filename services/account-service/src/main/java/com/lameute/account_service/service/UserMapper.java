package com.lameute.account_service.service;

import com.lameute.account_service.dto.UserResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lameute.account_service.dto.UserRequest;
import com.lameute.account_service.model.User;

import io.micrometer.common.util.StringUtils;

/*
 * maps UserRequest to User and vice versa
 */
@Service
public class UserMapper {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    
    /*converts UserRequest to User from response entity */
    public User toUser(UserRequest userRequest){
        User user = new User();
        user.setUsername(userRequest.username());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setPhoneNumber(userRequest.phoneNumber());

        return user;
    }

    public UserResponse toUserResponse(User user){
        return new UserResponse(
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }

    /*converts User to UserRequest to be send as response entity */
    public UserRequest fromUser(User user){
        UserRequest userRequest = new UserRequest(
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            user.getPhoneNumber()
        );

        return userRequest;
    }

    /*Update user attributes with UserRequest data */
    public void mergeUser(User user, UserRequest userRequest){
        if(StringUtils.isNotBlank(userRequest.email())){
            user.setEmail(userRequest.email());
        } 
        if(StringUtils.isNotBlank(userRequest.username())){
            user.setUsername(userRequest.username());
        } 
        if(StringUtils.isNotBlank(userRequest.password())){
            user.setPassword(passwordEncoder.encode(userRequest.password()));
        } 
        if(StringUtils.isNotBlank(userRequest.phoneNumber())){
            user.setPhoneNumber(userRequest.phoneNumber());
        } 
    }
    
}
