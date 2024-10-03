package com.lameute.ride_service.controller;

import com.lameute.ride_service.dto.RideRequest;
import com.lameute.ride_service.model.Ride;
import com.lameute.ride_service.service.RideService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ride")
public class RideController {
    @Autowired
    private RideService rideService;

    @PostMapping("/save")
    public ResponseEntity<Ride> saveRide(@RequestBody @Valid RideRequest rideRequest){
        return new ResponseEntity<>(rideService.saveRide(rideRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update/{idRide}")
    public Ride updateRide(@RequestBody RideRequest rideRequest,
                                 @PathVariable("idRide") long idRide){
        return rideService.updateRide(rideRequest,idRide);
    }

    @GetMapping("/all/{idUser}")
    public List<Ride> getAllUserRides(@PathVariable("idUser") long idUser){
        return rideService.getAllRidesByUserId(idUser);
    }

    @DeleteMapping("/delete/{idRide}")
    public void deleteRide(@PathVariable("idRide") long idRide){
        rideService.deleteRide(idRide);
    }

    @PutMapping("/startRide/{idRide}")
    public void startRide(@PathVariable("idRide") long idRide){
        rideService.startRide(idRide);
    }

    @PutMapping("/terminateRide/{idRide}")
    public void terminateRide(@PathVariable("idRide") long idRide){
        rideService.terminateRide(idRide);
    }
}
