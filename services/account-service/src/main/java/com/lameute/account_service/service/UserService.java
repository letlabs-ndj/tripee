package com.lameute.account_service.service;

import java.util.Optional;

import com.lameute.account_service.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lameute.account_service.dto.UserRequest;
import com.lameute.account_service.exceptions.EmailAlreadyUsedException;
import com.lameute.account_service.exceptions.UserNotFoundException;
import com.lameute.account_service.model.User;
import com.lameute.account_service.repo.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    public UserResponse getUserById(long id){
        return userMapper.toUserResponse(userRepo.findById(id)
                                                .orElseThrow(()->new UserNotFoundException("L'utilisateur avec l'identifiant : "+id+" est introuvable")));
    }

    public boolean existById(long id) {
        return userRepo.existsById(id);
    }
}
