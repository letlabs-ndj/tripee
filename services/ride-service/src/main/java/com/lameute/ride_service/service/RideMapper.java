package com.lameute.ride_service.service;

import com.lameute.ride_service.dto.RideRequest;
import com.lameute.ride_service.model.Ride;
import io.micrometer.common.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RideMapper {
    @Autowired
    private PlaceMapper placeMapper;

    public Ride toRide(RideRequest rideRequest){
        Ride ride = new Ride();
        ride.setArrivalPlace(placeMapper.toPlace(rideRequest.arrivalPlace()));
        ride.setPrice(rideRequest.price());
        ride.setDepartureDate(rideRequest.departureDate());
        ride.setDeparturePlace(placeMapper.toPlace(rideRequest.departurePlace()));
        ride.setDepartureTime(rideRequest.departureTime());
        ride.setUserId(rideRequest.userId());

        return ride;
    }

    /*Update rides attributes with RideRequest data */
    public void mergeRide(Ride ride, RideRequest rideRequest){
        if(rideRequest.arrivalPlace() != null){
            placeMapper.mergePlace(ride.getArrivalPlace(), rideRequest.arrivalPlace());
        }
        if(rideRequest.departurePlace() != null){
            placeMapper.mergePlace(ride.getDeparturePlace(), rideRequest.departurePlace());
        }
        if(rideRequest.departureTime() != null){
            ride.setDepartureTime(rideRequest.departureTime());
        }
        if(rideRequest.departureDate() != null){
            ride.setDepartureDate(rideRequest.departureDate());
        }
        if(StringUtils.isNotBlank(String.valueOf(rideRequest.price()))){
            ride.setPrice(rideRequest.price());
        }
        if(StringUtils.isNotBlank(String.valueOf(rideRequest.availablePlaces()))){
            ride.setAvailablePlaces(rideRequest.availablePlaces());
        }
    }
}
