package com.lameute.reservation_service.service;

import com.lameute.reservation_service.dto.ReservationRequest;
import com.lameute.reservation_service.dto.ReservationResponse;
import com.lameute.reservation_service.dto.UserResponse;
import com.lameute.reservation_service.model.Enums.ReservationStatus;
import com.lameute.reservation_service.model.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReservationMapper {

    /*Converts Reservation request to Reservation object */
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

    /*Converts Reservation object to Reservation response */
    public ReservationResponse toReservationResponse(Reservation reservation, UserResponse user){
        return new ReservationResponse(
                reservation.getId(),
                reservation.getReservationDate(),
                reservation.getReservationTime(),
                reservation.getReservedPlaces(),
                reservation.getPrice(),
                reservation.getReservationStatus(),
                reservation.getHasLuggage(),
                user
        );
    }

    /*Update reservation attributes with ReservationRequest data */
    public void mergeReservation(Reservation reservation, ReservationRequest request){
        if(request.hasLuggage() != null){
            reservation.setHasLuggage(request.hasLuggage());
        }
        if(request.reservedPlaces() != null){
            reservation.setReservedPlaces(request.reservedPlaces());
        }
        if(request.price() != null){
            reservation.setPrice(request.price());
        }
    }
}
