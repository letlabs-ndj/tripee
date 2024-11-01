package com.lameute.reservation_service.service;

import com.lameute.reservation_service.dto.RideResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(
        name = "ride-service",
        url = "${application.config.ride-url}"
)
public interface RideClient {
    @GetMapping("/exists/{idUser}")
    boolean checkRide(@PathVariable("idUser") long idUser);

    @PutMapping("/id/{idRide}/availablePlaces/restore/{numberOfPlaces}")
    void restoreAvailablePlaces(@PathVariable("idRide") long idRide,
                                @PathVariable("numberOfPlaces") int numberOfPlaces);

    @PutMapping("/id/{idRide}/availablePlaces/remove/{numberOfPlaces}")
    void removeAvailablePlaces(@PathVariable("idRide") long idRide,
                               @PathVariable("numberOfPlaces") int numberOfPlaces);

    @GetMapping("/id/{idRide}")
    RideResponse getRideById(@PathVariable("idRide") long idRide);
}
