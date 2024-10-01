package com.lameute.account_service.service;

import com.lameute.account_service.config.TwilioProperties;
import com.twilio.exception.ApiException;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    @Autowired
    private TwilioProperties twilioProperties;


    /*otp code sending */
    public boolean sendOtp(String phone) throws ApiException{
        Verification verification = Verification.creator(twilioProperties.getServiceId(), phone, "sms").create();
        if ("approved".equals(verification.getStatus()) || "pending".equals(verification.getStatus())) {
            return true;
        }
        return false;
    }

    /*otp code verfication */
    public boolean verifyOtp(String phone, String otpCode) throws ApiException{
        VerificationCheck verification = VerificationCheck.creator(twilioProperties.getServiceId())
        .setTo(phone)
        .setCode(otpCode)
        .create();

        if ("approved".equals(verification.getStatus())) {
            return true;
        }
        return false;
    }

}
