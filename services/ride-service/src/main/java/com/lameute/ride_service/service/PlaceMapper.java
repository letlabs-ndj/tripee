package com.lameute.ride_service.service;

import org.springframework.stereotype.Service;

import com.lameute.ride_service.dto.PlaceRequest;
import com.lameute.ride_service.model.Place;

import io.micrometer.common.util.StringUtils;

@Service
public class PlaceMapper {

    /*Converts place request to place object */
    public Place toPlace(PlaceRequest placeRequest){
        Place place = new Place();
        place.setDetails(placeRequest.details());
        place.setLatitude(placeRequest.latitude());
        place.setLongitude(placeRequest.longitude());
        place.setName(placeRequest.name());
        place.setTown(placeRequest.town());

        return place;
    }

    /*Update places attributes with placeRequest data */
    public void mergePlace(Place place, PlaceRequest placeRequest){
        if(StringUtils.isNotBlank(placeRequest.details())){
            place.setDetails(placeRequest.details());
        }
        if(StringUtils.isNotBlank(placeRequest.latitude())){
            place.setLatitude(placeRequest.latitude());
        }
        if(StringUtils.isNotBlank(placeRequest.longitude())){
            place.setLongitude(placeRequest.longitude());
        }
        if(StringUtils.isNotBlank(placeRequest.name())){
            place.setName(placeRequest.name());
        }
        if(StringUtils.isNotBlank(placeRequest.town())){
            place.setTown(placeRequest.town());
        }
    }
}
