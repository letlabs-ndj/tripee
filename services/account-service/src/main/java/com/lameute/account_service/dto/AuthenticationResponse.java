package com.lameute.account_service.dto;

public record AuthenticationResponse(
        long id,
        String username,
        String token
) {
}
