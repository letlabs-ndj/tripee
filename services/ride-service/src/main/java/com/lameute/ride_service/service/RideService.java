package com.lameute.ride_service.service;

import com.lameute.ride_service.dto.RideRequest;
import com.lameute.ride_service.exception.RideNotFoundException;
import com.lameute.ride_service.exception.UserNotFoundEXception;
import com.lameute.ride_service.model.Ride;
import com.lameute.ride_service.model.Enum.RideStatus;
import com.lameute.ride_service.repo.RideRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RideService {
    @Autowired
    private RideRepo rideRepo;

    @Autowired
    private RideMapper rideMapper;
    
    @Autowired
    private UserClient userClient;

    public Ride saveRide(RideRequest rideRequest) {
        if (userExist(rideRequest.userId())){ // We first check if the user creating the ride exists
            Ride ride = rideMapper.toRide(rideRequest); // If it's the case we get the ride object
            ride.setStatus(RideStatus.ON_HOLD);  //By default a ride is at the oh hold state wiating to start
            
            return rideRepo.save(ride); // then we save it to database
        }else {
            throw new UserNotFoundEXception("Cannot create ride :: No user with id :"
                    +rideRequest.userId()+" found"); // If no user is found we throw an exception
        }
    }

    public Ride updateRide(RideRequest rideRequest, long idRide) {
        if (userExist(rideRequest.userId())){ // We first check if the user creating the ride exists
            Ride existingRide = rideRepo.findById(idRide) // We get the the existing ride object if it exist
                    .orElseThrow(()-> new RideNotFoundException("No ride with id : "+idRide+" found"));

            rideMapper.mergeRide(existingRide, rideRequest); // We update the existing ride object with data from the the rideRequest object
            return rideRepo.save(existingRide); // We save back the existing object with updated data
        }else {
            throw new UserNotFoundEXception("Cannot update ride :: No user with id :"
                    +rideRequest.userId()+" found"); // If no user is found we throw an exception
        }
    }

    public List<Ride> getAllRidesByUserId(long userId){
        List<Ride> rides = rideRepo.findByUserId(userId)
                        .orElseThrow(()-> new RideNotFoundException("No rides for user with id : "+userId));

        return rides;
    }

//    public List<Ride> searchRide(String departurePlace, String arrivalPlace){
//        List<Ride> rides = rideRepo.searchRideByRoute(departurePlace,arrivalPlace)
//                .orElseThrow(()-> new RideNotFoundException("No rides that suit this route found"));
//
//        return rides;
//    }

    public void deleteRide(long idRide){
        rideRepo.deleteById(idRide);
    }

    public void startRide(long idRide){
        rideRepo.startRide(idRide);
    }

    public void terminateRide(long idRide){
        rideRepo.terminateRide(idRide);
    }

    /* Check if user exist*/
    public boolean userExist(long idUser){
        return userClient.checkUser(idUser);
    }
}
