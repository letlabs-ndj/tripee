package com.lameute.ride_service.service;

import com.lameute.ride_service.dto.RideRequest;
import com.lameute.ride_service.exception.InvalidUserException;
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

    /*create new ride */
    public Ride saveRide(RideRequest rideRequest, String imageUrl) {
        if (userExist(rideRequest.userId())){ // if user exist
            Ride ride = rideMapper.toRide(rideRequest); // If it's the case we get the ride object
            ride.setStatus(RideStatus.ON_HOLD);  //By default a ride is at the oh hold state wiating to start
            ride.getVehicle().setImagePath(imageUrl); // Set the car image url
            return rideRepo.save(ride);
        }else {
            throw new UserNotFoundEXception("No user with id : "+rideRequest.userId()+" exist");
        }

    }

    /*update a ride */
    public Ride updateRide(RideRequest rideRequest, long idRide) {
        Ride existingRide = rideRepo.findById(idRide) // We get the the existing ride object if it exist
                .orElseThrow(()-> new RideNotFoundException("No ride with id : "+idRide+" found"));

        if (rideRequest.userId() == existingRide.getUserId()){ // check if the user ids match
            rideMapper.mergeRide(existingRide, rideRequest); // if it's the case we update the existing ride object with data from the the rideRequest object
            return rideRepo.save(existingRide); // We save back the existing object with updated data
        }else {
            throw new InvalidUserException("User not authorized to update this ride data since id doesn't match");
        }

    }

    /*get all rides for a particular user */
    public List<Ride> getAllRidesByUserId(long userId){
        List<Ride> rides = rideRepo.findByUserId(userId)
                        .orElseThrow(()-> new RideNotFoundException("No rides for user with id : "+userId));

        return rides;
    }

    /*Search rides */
   public List<Ride> searchRide(String departurePlace, String arrivalPlace){
       List<Ride> rides = rideRepo.searchRide(departurePlace,arrivalPlace)
               .orElseThrow(()-> new RideNotFoundException("No rides that suit this route found"));

       return rides;
   }

    /*delete a ride */
    public void deleteRide(long idRide){
        rideRepo.deleteById(idRide);
    }

    /*start a ride */
    public void startRide(long idRide){
        rideRepo.startRide(idRide);
    }

    /*terminate a ride */
    public void terminateRide(long idRide){
        rideRepo.terminateRide(idRide);
    }

    /* Check if user exist*/
    public boolean userExist(long idUser){
        return userClient.checkUser(idUser);
    }
}
