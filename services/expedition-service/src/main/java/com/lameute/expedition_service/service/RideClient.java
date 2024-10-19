package com.lameute.expedition_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "ride-service",
        url = "${application.config.ride-url}"
)
public interface RideClient {
    @GetMapping("/exists/{idUser}")
    boolean checkRide(@PathVariable("idUser") long idUser);
}
