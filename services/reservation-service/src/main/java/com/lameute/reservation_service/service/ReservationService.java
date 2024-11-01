package com.lameute.reservation_service.service;

import com.lameute.reservation_service.dto.ReservationRequest;
import com.lameute.reservation_service.dto.ReservationResponse;
import com.lameute.reservation_service.dto.UserResponse;
import com.lameute.reservation_service.exceptions.InvalidUserException;
import com.lameute.reservation_service.exceptions.ReservationNotFoundException;
import com.lameute.reservation_service.exceptions.RideNotFoundException;
import com.lameute.reservation_service.exceptions.UserNotFoundEXception;
import com.lameute.reservation_service.model.Enums.ReservationStatus;
import com.lameute.reservation_service.model.Reservation;
import com.lameute.reservation_service.repo.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private UserClient userClient;

    @Autowired
    private RideClient rideClient;

    @Autowired
    private ReservationMapper reservationMapper;

    public List<ReservationResponse> getAllUserReservations(long userId){
        List<Reservation> reservations = reservationRepo.findByUserId(userId)
                .orElseThrow(()->new ReservationNotFoundException("No reservation found for user with id : "+userId));

        List<ReservationResponse> reservationResponses = new ArrayList<>();
        for (Reservation reservation : reservations) {
            var user = userClient.getUserById(reservation.getUserId());
            var ride = rideClient.getRideById(reservation.getRideId());
            reservationResponses.add(reservationMapper.toReservationResponse(
                    reservation,
                    user,
                    ride.departurePlace().getName(),
                    ride.arrivalPlace().getName()
            ));
        }

        return reservationResponses;
    }

    public List<ReservationResponse> getRideReservations(long rideId){
        List<Reservation> reservations =  reservationRepo.findByRideId(rideId)
                .orElseThrow(()->new ReservationNotFoundException("No reservation found for ride with id : "+rideId));

        List<ReservationResponse> reservationResponses = new ArrayList<>();
        for (Reservation reservation : reservations) {
            var user = userClient.getUserById(reservation.getUserId());
            var ride = rideClient.getRideById(reservation.getRideId());
            reservationResponses.add(reservationMapper.toReservationResponse(
                    reservation,
                    user,
                    ride.departurePlace().getName(),
                    ride.arrivalPlace().getName()
                    ));
        }

        return reservationResponses;
    }

    public List<ReservationResponse> getRideAcceptedReservations(long rideId) {
        List<Reservation> reservations =  reservationRepo.findAcceptedReservations(rideId)
                .orElseThrow(()->new ReservationNotFoundException("No accepted reservation found for ride with id : "+rideId));

        List<ReservationResponse> reservationResponses = new ArrayList<>();
        for (Reservation reservation : reservations) {
            var user = userClient.getUserById(reservation.getUserId());
            var ride = rideClient.getRideById(reservation.getRideId());
            reservationResponses.add(reservationMapper.toReservationResponse(
                    reservation,
                    user,
                    ride.departurePlace().getName(),
                    ride.arrivalPlace().getName()
            ));
        }

        return reservationResponses;
    }

    public Reservation createReservation(ReservationRequest request){
        if (!userExist(request.userId())){
            throw new UserNotFoundEXception("No user with id : "+request.userId()+" found");
        }
        if (!rideExist(request.rideId())){
            throw new RideNotFoundException("No ride with id : "+request.rideId()+" found");
        }
        Reservation reservation = reservationMapper.toReservation(request);
        return reservationRepo.save(reservation);
    }

    public Reservation updateReservation(ReservationRequest request, long reservationId){
        Reservation existingReservation = reservationRepo.findById(reservationId)
                        .orElseThrow(() -> new ReservationNotFoundException("No reservation with id : "+reservationId+" found"));

        if (request.userId() != existingReservation.getUserId()){
            throw new InvalidUserException("User not authorized to update this reservation data since user id doesn't match");
        }
        if (request.rideId() != existingReservation.getRideId()){
            throw new InvalidUserException("User not authorized to update this reservation data since ride id doesn't match");
        }
        reservationMapper.mergeReservation(existingReservation,request);
        return reservationRepo.save(existingReservation);
    }

    public void deleteReservation(long reservationId){
        reservationRepo.deleteById(reservationId);
    }

    public void deleteReservationByRide(long rideId) {
        reservationRepo.deleteByRideId(rideId);
    }

    public void acceptReservation(long reservationId,long rideId, int numberOfPlaces){
        rideClient.removeAvailablePlaces(rideId,numberOfPlaces);
        reservationRepo.updateReservationStatus(reservationId, ReservationStatus.ACCEPTED.name());
    }

    public void refuseReservation(long reservationId){
        reservationRepo.updateReservationStatus(reservationId, ReservationStatus.REFUSED.name());
    }

    public void cancelReservation(long reservationId, long rideId, int numberOfPlaces) {
        rideClient.restoreAvailablePlaces(rideId,numberOfPlaces);
        reservationRepo.updateReservationStatus(reservationId, ReservationStatus.ON_HOLD.name());
    }

    private boolean userExist(long userId){
        return userClient.checkUser(userId);
    }

    private boolean rideExist(long rideId){
        return rideClient.checkRide(rideId);
    }

}
