package com.lameute.reservation_service.service;

import com.lameute.reservation_service.dto.ReservationRequest;
import com.lameute.reservation_service.model.Enums.ReservationStatus;
import com.lameute.reservation_service.model.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReservationMapper {

    public Reservation toReservation(ReservationRequest request){
        Reservation reservation = new Reservation();
        reservation.setReservationDate(LocalDate.now());
        reservation.setReservationTime(LocalTime.now());
        reservation.setReservationStatus(ReservationStatus.ON_HOLD);
        reservation.setReservedPlaces(request.reservedPlaces());
        reservation.setPrice(request.price());
        reservation.setHasLuggage(request.hasLuggage());
        reservation.setUserId(request.userId());
        reservation.setRideId(request.rideId());

        return reservation;
    }

    /*Update reservation attributes with ReservationRequest data */
    public void mergeReservation(Reservation reservation, ReservationRequest request){
        if(request.hasLuggage() != null){
            reservation.setHasLuggage(request.hasLuggage());
        }
        if(request.reservedPlaces() != null){
            reservation.setReservedPlaces(request.reservedPlaces());
        }
        if(request.reservedPlaces() != null){
            reservation.setReservedPlaces(request.reservedPlaces());
        }
    }
}
