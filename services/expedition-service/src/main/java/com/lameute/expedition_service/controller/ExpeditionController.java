package com.lameute.expedition_service.controller;

import com.lameute.expedition_service.dto.ExpeditionRequest;
import com.lameute.expedition_service.dto.ExpeditionResponse;
import com.lameute.expedition_service.model.Expedition;
import com.lameute.expedition_service.service.ExpeditionMapper;
import com.lameute.expedition_service.service.ExpeditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expeditions")
public class ExpeditionController {
    @Autowired
    private ExpeditionService expeditionService;

    @Autowired
    private ExpeditionMapper expeditionMapper;

    @GetMapping("/user/{userId}/all")
    public ResponseEntity<List<ExpeditionResponse>> getAllUserExpeditions(@PathVariable("userId") long userId){
        return new ResponseEntity<>(expeditionService.getAllUserExpeditions(userId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/ride/{rideId}/all")
    public ResponseEntity<List<ExpeditionResponse>> getRideExpeditions(@PathVariable("rideId") long rideId){
        return new ResponseEntity<>(expeditionService.getRideExpeditions(rideId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/ride/{rideId}/accepted")
    public ResponseEntity<List<ExpeditionResponse>> getRideAcceptedReservations(@PathVariable("rideId") long rideId){
        return new ResponseEntity<>(expeditionService.getRideAcceptedReservations(rideId), HttpStatus.ACCEPTED);
    }

    @PostMapping("/save")
    public ResponseEntity<Expedition> createExpedition(@RequestBody ExpeditionRequest expeditionRequest){
        return new ResponseEntity<>(expeditionService.createExpedition(expeditionRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update/{idExp}")
    public ResponseEntity<Expedition> updateExpedition(@RequestBody ExpeditionRequest request, @PathVariable("idExp") long expeditionId){
        return new ResponseEntity<>(expeditionService.updateExpedition(request,expeditionId),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{idExp}")
    public void deleteExpedition(@PathVariable("idExp") long expeditionId){
        expeditionService.deleteExpedition(expeditionId);
    }

    @PutMapping("/accept/{idExp}")
    public void acceptExpedition(@PathVariable("idExp") long expeditionId){
        expeditionService.acceptExpedition(expeditionId);
    }

    @PutMapping("/refuse/{idExp}")
    public void refuseExpedition(@PathVariable("idExp") long expeditionId){
        expeditionService.refuseExpedition(expeditionId);
    }

    @PutMapping("/cancel/{idExp}")
    public void cancelReservation(long expeditionId) {
        expeditionService.cancelExxpedition(expeditionId);
    }
}
