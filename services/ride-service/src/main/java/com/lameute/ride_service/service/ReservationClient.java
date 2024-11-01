package com.lameute.ride_service.service;

import com.lameute.ride_service.dto.ReservationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "reservation-service",
        url = "${application.config.reservation-url}"
)
public interface ReservationClient {

    @GetMapping("/ride/{rideId}/accepted")
    List<ReservationResponse> getRideAcceptedReservations(
            @PathVariable("rideId") long rideId);

    @DeleteMapping("/delete/ride/{idRide}")
    void deleteReservationByRide(@PathVariable("idRide") long rideId);
}
