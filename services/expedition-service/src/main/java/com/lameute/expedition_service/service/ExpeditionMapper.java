package com.lameute.expedition_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lameute.expedition_service.dto.ExpeditionRequest;
import com.lameute.expedition_service.dto.ExpeditionResponse;
import com.lameute.expedition_service.dto.UserResponse;
import com.lameute.expedition_service.model.Enums.ExpeditionStatus;
import com.lameute.expedition_service.model.Expedition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ExpeditionMapper {
    @Autowired
    PacketMapper packetMapper;

    /*Converts Expedition request to Expedition object */
    public Expedition toExpedition(ExpeditionRequest request){
        Expedition expedition = new Expedition();
        expedition.setExpeditionDate(LocalDate.now());
        expedition.setExpeditionTime(LocalTime.now());
        expedition.setExpeditionStatus(ExpeditionStatus.ON_HOLD);
        expedition.setPrice(request.price());
        expedition.setEmailToContact(request.emailToContact());
        expedition.setPacket(packetMapper.toPacket(request.packet()));
        expedition.setUserId(request.userId());
        expedition.setRideId(request.rideId());

        return expedition;
    }

    /*Converts Expedition object to Expedition response */
    public ExpeditionResponse toExpeditionResponse(Expedition expedition, UserResponse user,
                                                   String departurePlace, String arrivalPlace){
        return new ExpeditionResponse(
                expedition.getId(),
                expedition.getRideId(),
                expedition.getExpeditionDate(),
                expedition.getExpeditionTime(),
                expedition.getEmailToContact(),
                expedition.getPrice(),
                expedition.getExpeditionStatus(),
                expedition.getPacket(),
                departurePlace,
                arrivalPlace,
                user
        );
    }

    /*Maps ride json String to ride object */
    public ExpeditionRequest toRideRequest(String data){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            ExpeditionRequest expeditionRequest = mapper.readValue(data, ExpeditionRequest.class);
            return expeditionRequest;
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }
        return null;
    }

    /*Update expedition attributes with ExpeditionRequest data */
    public void mergeExpedition(Expedition expedition, ExpeditionRequest request){
        if(request.packet() != null){
            packetMapper.mergePacket(expedition.getPacket(),request.packet());
        }
        if(request.emailToContact() != null){
            expedition.setEmailToContact(request.emailToContact());
        }
        if(request.price() != null){
            expedition.setPrice(request.price());
        }
    }
}
