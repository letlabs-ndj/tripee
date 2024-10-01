package com.lameute.account_service.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lameute.account_service.exceptions.EmailAlreadyUsedException;
import com.lameute.account_service.exceptions.UserNotFoundException;
import com.lameute.account_service.model.User;
import com.lameute.account_service.repo.UserRepo;

@Service
public class UserService {
    
    @Autowired
    private UserRepo userRepo;

    public User creatUser(User user){
        Optional<User> userObj = userRepo.findByEmail(user.getEmail());
        if (!userObj.isEmpty()) {
            throw new EmailAlreadyUsedException();
        }
        return userRepo.save(user);
    }

    public User getUserById(long id){
        return userRepo.findById(id).orElseThrow(()->new RuntimeException("no user with tis id found"));
    }

    public User getUserByEmail(String email){
        return userRepo.findByEmail(email).orElseThrow(()->new UserNotFoundException(email));
    }
}
