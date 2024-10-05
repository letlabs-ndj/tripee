package com.lameute.ride_service.service;

import org.springframework.stereotype.Service;

import com.lameute.ride_service.dto.VehicleRequest;
import com.lameute.ride_service.model.Vehicle;

import io.micrometer.common.util.StringUtils;

@Service
public class VehicleMapper {

    /*Converts vehicle request to vehicle object */
    public Vehicle toVehicle(VehicleRequest vehicleRequest){
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleBrand(vehicleRequest.vehicleBrand());
        vehicle.setRegistrationNumber(vehicleRequest.registrationNumber());
        return vehicle;
    }

        /*Update vehicle attributes with vehicle request data */
    public void mergeVehicle(Vehicle vehicle, VehicleRequest vehicleRequest){
        if(StringUtils.isNotBlank(vehicleRequest.registrationNumber())){
            vehicle.setRegistrationNumber(vehicleRequest.registrationNumber());
        }
        if(StringUtils.isNotBlank(vehicleRequest.vehicleBrand())){
            vehicle.setRegistrationNumber(vehicleRequest.vehicleBrand());
        }
    }
}
