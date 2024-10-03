package com.lameute.account_service.service;

import java.util.Optional;

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

    public User createUser(UserRequest userRequest){
        User user = userMapper.toUser(userRequest);
        Optional<User> userObj = userRepo.findByEmail(user.getEmail());
        if (!userObj.isEmpty()) {
            throw new EmailAlreadyUsedException();
        }
        return userRepo.save(user);
    }

    public User getUserById(long id){
        return userRepo.findById(id).orElseThrow(()->new UserNotFoundException(id));
    }

    public User updateUser(UserRequest userRequest, long id){
        User user = userRepo.findById(id)
                    .orElseThrow(()-> new UserNotFoundException(id));

        userMapper.mergeUser(user, userRequest);
        return userRepo.save(user);
    }

    public boolean existById(long id) {
        return userRepo.existsById(id);
    }
}
