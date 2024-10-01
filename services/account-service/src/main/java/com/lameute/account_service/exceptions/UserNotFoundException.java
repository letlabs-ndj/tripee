package com.lameute.account_service.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(long id) {
        super("L'utilisateur avec l'identifiant : "+id+" est introuvable");
    }
}
