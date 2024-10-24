package com.lameute.ride_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lameute.ride_service.dto.RideRequest;
import com.lameute.ride_service.dto.RideResponse;
import com.lameute.ride_service.dto.UserResponse;
import com.lameute.ride_service.model.Ride;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RideMapper {
    @Autowired
    private PlaceMapper placeMapper;

    @Autowired
    private VehicleMapper vehicleMapper;

    /*Converts ride request to ride object */
    public Ride toRide(RideRequest rideRequest){
        Ride ride = new Ride();
        ride.setArrivalPlace(placeMapper.toPlace(rideRequest.arrivalPlace()));
        ride.setPrice(rideRequest.price());
        ride.setDepartureDate(rideRequest.departureDate());
        ride.setDeparturePlace(placeMapper.toPlace(rideRequest.departurePlace()));
        ride.setVehicle(vehicleMapper.toVehicle(rideRequest.vehicle()));
        ride.setMaxWeight(rideRequest.maxWeight());
        ride.setDoExpedition(rideRequest.doExpedition());
        ride.setDepartureTime(rideRequest.departureTime());
        ride.setAvailablePlaces(rideRequest.availablePlaces());
        ride.setUserId(rideRequest.userId());

        return ride;
    }

    /*Maps ride json String to ride object */
    public RideRequest toRideRequest(String data){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            RideRequest rideRequest = mapper.readValue(data, RideRequest.class);
            return rideRequest;
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }
        return null;
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
        if(rideRequest.price() != null){
            ride.setPrice(rideRequest.price());
        }
        if(rideRequest.doExpedition() != null){
            ride.setDoExpedition(rideRequest.doExpedition());
        }
        if(rideRequest.maxWeight() != null){
            ride.setPrice(rideRequest.maxWeight());
        }
        if(rideRequest.availablePlaces() != null){
            ride.setAvailablePlaces(rideRequest.availablePlaces());
        }
    }

    public RideResponse toRideResponse(Ride ride, UserResponse userResponse) {
        return new RideResponse(
                ride.getId(),
                ride.getDepartureDate(),
                ride.getDepartureTime(),
                ride.getDeparturePlace(),
                ride.getArrivalPlace(),
                ride.getVehicle(),
                ride.getPrice(),
                ride.getAvailablePlaces(),
                ride.getDoExpedition(),
                ride.getMaxWeight(),
                ride.getStatus(),
                userResponse
        );
    }
}
