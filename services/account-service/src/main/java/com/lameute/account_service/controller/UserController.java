package com.lameute.account_service.controller;

import com.lameute.account_service.dto.AuthenticationRequest;
import com.lameute.account_service.dto.AuthenticationResponse;
import com.lameute.account_service.dto.UserResponse;
import com.lameute.account_service.exceptions.OtpVerificationFailedException;
import com.lameute.account_service.model.User;
import com.lameute.account_service.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lameute.account_service.dto.UserRequest;
import com.lameute.account_service.exceptions.OtpNotSentException;
import com.lameute.account_service.service.OtpService;
import com.lameute.account_service.service.UserService;
import com.twilio.exception.ApiException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private OtpService otpService;

    @Autowired
    private AuthenticationService authService;

    @PostMapping("/auth/sendOtp/{phone}")
    public ResponseEntity<String> sendOtp (@PathVariable("phone") String phone) throws ApiException{
        if(otpService.sendOtp(phone.trim())){
            log.info("Otp sent successfully");
            return new ResponseEntity<>("Code Otp transféré avec succès",HttpStatus.OK);
        } 
        else {
            throw new OtpNotSentException();
        } 
    }

    @PostMapping("/auth/register/{otpCode}")
    public ResponseEntity<AuthenticationResponse> registerUser (@PathVariable("otpCode") String otpCode, @RequestBody @Valid UserRequest userRequest) throws ApiException{
        if (otpService.verifyOtp(userRequest.phoneNumber(),otpCode)) {
            log.info("Otp verified successfully");
            return new ResponseEntity<>(authService.registerUser(userRequest),HttpStatus.CREATED);
        }else{
            throw new OtpVerificationFailedException();
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthenticationResponse> login (@RequestBody @Valid AuthenticationRequest request) {
        AuthenticationResponse authResponse = authService.authenticate(request);
        log.info("User {} logged in",authResponse.username());
        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponse> getUserById (@PathVariable("id") long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail (@PathVariable("email") String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AuthenticationResponse> updateUser (@RequestBody UserRequest userRequest,@PathVariable("id") long id){
        return new ResponseEntity<>(userService.updateUser(userRequest, id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> checkUser(@PathVariable("id") long id) {
        return new ResponseEntity<>(userService.existById(id), HttpStatus.OK);
    }
}
