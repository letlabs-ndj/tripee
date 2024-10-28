package com.lameute.account_service.service;

import com.lameute.account_service.dto.AuthenticationResponse;
import com.lameute.account_service.dto.UserResponse;
import com.lameute.account_service.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lameute.account_service.dto.UserRequest;
import com.lameute.account_service.exceptions.UserNotFoundException;
import com.lameute.account_service.model.User;
import com.lameute.account_service.repo.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtService jwtService;

    public UserResponse getUserById(long id){
        return userMapper.toUserResponse(userRepo.findById(id)
                                                .orElseThrow(()->new UserNotFoundException("L'utilisateur avec l'identifiant : "+id+" est introuvable")));
    }

    public boolean existById(long id) {
        return userRepo.existsById(id);
    }

    public AuthenticationResponse updateUser(UserRequest userRequest, long id){
        User user = userRepo.findById(id)
                .orElseThrow(()-> new UserNotFoundException("L'utilisateur avec l'identifiant : "+id+" est introuvable"));
        userMapper.mergeUser(user, userRequest);

        user = userRepo.save(user);
        String token = jwtService.generateToken(user.getEmail());

        return new AuthenticationResponse(
                user.getId(),
                user.getUsername(),
                token
        );
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("No user with email : "+email+" not found"));
    }

    public boolean isEmpty(){
        return userRepo.count() < 1;
    }
}
