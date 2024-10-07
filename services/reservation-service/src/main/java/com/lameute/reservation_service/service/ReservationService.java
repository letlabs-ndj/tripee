package com.lameute.reservation_service.service;

import com.lameute.reservation_service.dto.ReservationRequest;
import com.lameute.reservation_service.exceptions.ReservationNotFoundException;
import com.lameute.reservation_service.model.Reservation;
import com.lameute.reservation_service.repo.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private ReservationMapper reservationMapper;

    public List<Reservation> getAllUserReservations(long userId){
        return reservationRepo.findByUserId(userId)
                .orElseThrow(()->new ReservationNotFoundException("No reservation found for user with id : "+userId));
    }

    public Reservation createReservation(ReservationRequest request){
        Reservation reservation = reservationMapper.toReservation(request);
        return reservationRepo.save(reservation);
    }

    public Reservation updateReservation(ReservationRequest request, long reservationId){
        Reservation existingReservation = reservationRepo.findById(reservationId)
                        .orElseThrow(() -> new ReservationNotFoundException("No reservation with id : "+reservationId+" found"));

        reservationMapper.mergeReservation(existingReservation,request);
        return reservationRepo.save(existingReservation);
    }

    public void deleteReservation(long reservationId){
        reservationRepo.deleteById(reservationId);
    }
}
