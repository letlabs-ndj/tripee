package com.lameute.account_service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("twilio")
@Data
public class TwilioProperties {
    private String accountSid;
    private String authToken;
    private String serviceId;
}
