package com.lameute.ride_service.service;

import com.lameute.ride_service.dto.ExpeditionResponse;
import com.lameute.ride_service.dto.ReservationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "expedition-service",
        url = "${application.config.expedition-url}"
)
public interface ExpeditionClient {
    @GetMapping("/ride/{rideId}/accepted")
    List<ExpeditionResponse> getRideAcceptedExpeditions(
            @PathVariable("rideId") long rideId);

    @DeleteMapping("/delete/ride/{idRide}")
    void deleteExpeditionByRide(@PathVariable("idRide") long rideId);
}
