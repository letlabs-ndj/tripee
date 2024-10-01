package com.lameute.account_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lameute.account_service.dto.UserRequest;
import com.lameute.account_service.exceptions.OtpNotSentException;
import com.lameute.account_service.exceptions.OtpVerificationFailedException;
import com.lameute.account_service.model.User;
import com.lameute.account_service.service.OtpService;
import com.lameute.account_service.service.UserService;
import com.twilio.exception.ApiException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private OtpService otpService;


    @PostMapping("/sendOtp/{phone}")
    public ResponseEntity<String> sendOtp (@PathVariable("phone") String phone) throws ApiException{
        if(otpService.sendOtp(phone.trim())){
            return new ResponseEntity<>("Code Otp transféré avec succès",HttpStatus.OK);
        } 
        else {
            throw new OtpNotSentException();
        } 
    }

    @PostMapping("/create/{otpCode}")
    public ResponseEntity<User> createUser (@PathVariable("otpCode") String otpCode, @RequestBody UserRequest userRequest) throws ApiException{
        if (otpService.verifyOtp(userRequest.phoneNumber(),otpCode)) {
            return new ResponseEntity<>(userService.createUser(userRequest),HttpStatus.CREATED);
        }else{
            throw new OtpVerificationFailedException();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById (@PathVariable("id") long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser (@RequestBody UserRequest userRequest,@PathVariable("id") long id){
        return new ResponseEntity<>(userService.updateUser(userRequest, id), HttpStatus.ACCEPTED);
    }
}
