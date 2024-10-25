package com.lameute.chat_service.service;

import com.lameute.chat_service.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "account-service",
        url = "${application.config.account-url}"
)
public interface UserClient {
    @GetMapping("/exists/{idUser}")
    boolean checkUser(@PathVariable("idUser") long idUser);

    @GetMapping("/email/{email}")
    UserResponse getUserByEmail(@PathVariable("email") String email);
}
