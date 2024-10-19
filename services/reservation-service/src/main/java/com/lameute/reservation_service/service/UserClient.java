package com.lameute.reservation_service.service;

import com.lameute.reservation_service.dto.UserResponse;
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

    @GetMapping("/{idUser}")
    UserResponse getUserById(@PathVariable("idUser") long idUser);
}