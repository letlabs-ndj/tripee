package com.lameute.expedition_service.service;

import com.lameute.expedition_service.dto.ExpeditionRequest;
import com.lameute.expedition_service.dto.ExpeditionResponse;
import com.lameute.expedition_service.exceptions.ExpeditionNotFoundException;
import com.lameute.expedition_service.exceptions.InvalidUserException;
import com.lameute.expedition_service.exceptions.RideNotFoundException;
import com.lameute.expedition_service.exceptions.UserNotFoundEXception;
import com.lameute.expedition_service.model.Enums.ExpeditionStatus;
import com.lameute.expedition_service.model.Expedition;
import com.lameute.expedition_service.repo.ExpeditionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpeditionService {
    @Autowired
    private ExpeditionRepo expeditionRepo;

    @Autowired
    private UserClient userClient;

    @Autowired
    private RideClient rideClient;

    @Autowired
    private ExpeditionMapper expeditionMapper;

    public List<Expedition> getAllUserExpeditions(long userId){
        return expeditionRepo.findByUserId(userId)
                .orElseThrow(()->new ExpeditionNotFoundException("No expedition found for user with id : "+userId));
    }

    public List<ExpeditionResponse> getRideExpeditions(long rideId){
        List<Expedition> expeditions =  expeditionRepo.findByRideId(rideId)
                .orElseThrow(()->new ExpeditionNotFoundException("No expedition found for ride with id : "+rideId));

        List<ExpeditionResponse> expeditionResponses = new ArrayList<>();
        for (Expedition expedition : expeditions) {
            var user = userClient.getUserById(expedition.getUserId());
            expeditionResponses.add(expeditionMapper.toExpeditionResponse(expedition,user));
        }

        return expeditionResponses;
    }

    public Expedition createExpedition(ExpeditionRequest request, String packetImageUrl){
        if (!userExist(request.userId())){
            throw new UserNotFoundEXception("No user with id : "+request.userId()+" found");
        }
        if (!rideExist(request.rideId())){
            throw new RideNotFoundException("No ride with id : "+request.rideId()+" found");
        }
        Expedition expedition = expeditionMapper.toExpedition(request);
        expedition.getPacket().setPacketImage(packetImageUrl);
        expedition.setExpeditionStatus(ExpeditionStatus.ON_HOLD);
        return expeditionRepo.save(expedition);
    }

    public Expedition updateExpedition(ExpeditionRequest request, long expeditionId){
        Expedition existingExpedition = expeditionRepo.findById(expeditionId)
                .orElseThrow(() -> new ExpeditionNotFoundException("No expedition with id : "+expeditionId+" found"));

        if (request.userId() != existingExpedition.getUserId()){
            throw new InvalidUserException("User not authorized to update this expedition data since user id doesn't match");
        }
        if (request.rideId() != existingExpedition.getRideId()){
            throw new InvalidUserException("User not authorized to update this expedition data since ride id doesn't match");
        }
        expeditionMapper.mergeExpedition(existingExpedition,request);
        return expeditionRepo.save(existingExpedition);
    }

    public void deleteExpedition(long expeditionId){
        expeditionRepo.deleteById(expeditionId);
    }

    public void acceptExpedition(long expeditionId){
        expeditionRepo.updateExpeditionStatus(expeditionId, ExpeditionStatus.ACCEPTED.name());
    }

    public void refuseExpedition(long expeditionId){
        expeditionRepo.updateExpeditionStatus(expeditionId, ExpeditionStatus.REFUSED.name());
    }

    private boolean userExist(long userId){
        return userClient.checkUser(userId);
    }

    private boolean rideExist(long rideId){
        return rideClient.checkRide(rideId);
    }
}
