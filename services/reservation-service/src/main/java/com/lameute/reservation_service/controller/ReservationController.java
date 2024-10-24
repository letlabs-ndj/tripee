package com.lameute.reservation_service.controller;

import com.lameute.reservation_service.dto.ReservationRequest;
import com.lameute.reservation_service.dto.ReservationResponse;
import com.lameute.reservation_service.model.Reservation;
import com.lameute.reservation_service.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/user/{userId}/all")
    public ResponseEntity<List<Reservation>> getAllUserReservations(@PathVariable("userId") long userId){
        return new ResponseEntity<>(reservationService.getAllUserReservations(userId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/ride/{rideId}/all")
    public ResponseEntity<List<ReservationResponse>> getRideReservations(@PathVariable("rideId") long rideId){
        return new ResponseEntity<>(reservationService.getRideReservations(rideId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/ride/{rideId}/accepted")
    public ResponseEntity<List<ReservationResponse>> getRideAcceptedReservations(@PathVariable("rideId") long rideId){
        return new ResponseEntity<>(reservationService.getRideAcceptedReservations(rideId), HttpStatus.ACCEPTED);
    }

    @PostMapping("/create")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest request){
        return new ResponseEntity<>(reservationService.createReservation(request),HttpStatus.CREATED);
    }

    @PutMapping("/update/{idRes}")
    public ResponseEntity<Reservation> updateReservation(@RequestBody ReservationRequest request, @PathVariable("idRes") long reservationId){
        return new ResponseEntity<>(reservationService.updateReservation(request,reservationId),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{idRes}")
    public void deleteReservation(@PathVariable("idRes") long reservationId){
        reservationService.deleteReservation(reservationId);
    }

    @PutMapping("/accept/{idRes}/rides/{idRide}/{numberOfPlaces}")
    public void acceptReservation(@PathVariable("idRes") long reservationId,
                                  @PathVariable("idRide") long rideId,
                                  @PathVariable("numberOfPlaces") int numberOfPlaces){
        reservationService.acceptReservation(reservationId,rideId,numberOfPlaces);
    }

    @PutMapping("/refuse/{idRes}")
    public void refuseReservation(@PathVariable("idRes") long reservationId){
        reservationService.refuseReservation(reservationId);
    }

    @PutMapping("/cancel/{idRes}/rides/{idRide}/{numberOfPlaces}")
    public void cancelReservation(@PathVariable("idRes") long reservationId,
                                  @PathVariable("idRide") long rideId,
                                  @PathVariable("numberOfPlaces") int numberOfPlaces){
        reservationService.cancelReservation(reservationId,rideId,numberOfPlaces);
    }
}
