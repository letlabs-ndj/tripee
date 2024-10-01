package com.lameute.account_service.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("L'utilisateur avec l'email : "+email+" est introuvable");
    }
}
