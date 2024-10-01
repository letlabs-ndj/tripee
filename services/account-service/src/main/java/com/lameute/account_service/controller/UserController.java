package com.lameute.account_service.controller;

import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lameute.account_service.dto.UserRequest;
import com.lameute.account_service.exceptions.OtpRequestErrorException;
import com.lameute.account_service.exceptions.OtpVerificationFailedException;
import com.lameute.account_service.model.User;
import com.lameute.account_service.service.OtpService;
import com.lameute.account_service.service.UserMapper;
import com.lameute.account_service.service.UserService;
import com.twilio.exception.ApiException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserMapper userMapper;


    @PostMapping("/sendOtp/{phone}")
    public ResponseEntity<String> sendOtp (@PathVariable("phone") String phone) throws ApiException{
        if(otpService.sendOtp(phone.trim())){
            return new ResponseEntity<>("Code Otp transféré avec succès",HttpStatus.OK);
        } 
        else {
            System.out.println("conn");
            throw new OtpRequestErrorException();
        } 
    }

    @PostMapping("/create/{otpCode}")
    public ResponseEntity<?> createUser (@PathVariable("otpCode") String otpCode, @RequestBody UserRequest userRequest) throws ApiException{
        if (otpService.verifyOtp(userRequest.phoneNumber(),otpCode)) {
            System.out.println("ok");
            User user = userMapper.toUser(userRequest);
            return new ResponseEntity<>(userService.creatUser(user),HttpStatus.CREATED);
        }else{
            throw new OtpVerificationFailedException();
        }
    }
    
    @GetMapping("/{email}")
    public ResponseEntity<UserRequest> getUserByEmail (@PathVariable("email") String email) {
        UserRequest userRequest = userMapper.fromUser(userService.getUserByEmail(email));
        return new ResponseEntity<>(userRequest, HttpStatus.OK);
    }
}
