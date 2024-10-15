package com.lameute.account_service.service;

import com.lameute.account_service.dto.AuthenticationRequest;
import com.lameute.account_service.dto.AuthenticationResponse;
import com.lameute.account_service.dto.UserRequest;
import com.lameute.account_service.exceptions.EmailAlreadyUsedException;
import com.lameute.account_service.exceptions.UserNotFoundException;
import com.lameute.account_service.model.User;
import com.lameute.account_service.repo.UserRepo;
import com.lameute.account_service.security.CustomUserDetailService;
import com.lameute.account_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse registerUser(UserRequest userRequest){
        User user = userMapper.toUser(userRequest);
        Optional<User> userObj = userRepo.findByEmail(user.getEmail());
        if (!userObj.isEmpty()) {
            throw new EmailAlreadyUsedException();
        }
        user = userRepo.save(user);
        String token = jwtService.generateToken(user.getEmail());

        return new AuthenticationResponse(
                user.getId(),
                user.getUsername(),
                token
        );
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authRequest)
            throws BadCredentialsException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.email(),
                        authRequest.password()
                )
        );

        User user = userRepo.findByEmail(authRequest.email())
                .orElseThrow(()->new UserNotFoundException("L'utilisateur avec l'email : "+authRequest.email()+" n'exist pas"));
        String token = jwtService.generateToken(user.getEmail());
        return new AuthenticationResponse(
                user.getId(),
                user.getUsername(),
                token
        );
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
}
