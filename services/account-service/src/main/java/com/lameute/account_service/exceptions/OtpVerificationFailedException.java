package com.lameute.account_service.exceptions;

public class OtpVerificationFailedException extends RuntimeException{
    public OtpVerificationFailedException() {
        super("Vérification du code d'authentification OTP a échoué.");
    }
}