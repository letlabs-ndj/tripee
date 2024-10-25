package com.lameute.expedition_service.controller;

import com.lameute.expedition_service.dto.ExpeditionRequest;
import com.lameute.expedition_service.dto.ExpeditionResponse;
import com.lameute.expedition_service.model.Expedition;
import com.lameute.expedition_service.service.ExpeditionMapper;
import com.lameute.expedition_service.service.ExpeditionService;
import com.lameute.expedition_service.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/expeditions")
public class ExpeditionController {
    @Autowired
    private ExpeditionService expeditionService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ExpeditionMapper expeditionMapper;

    @GetMapping("/user/{userId}/all")
    public ResponseEntity<List<Expedition>> getAllUserExpeditions(@PathVariable("userId") long userId){
        return new ResponseEntity<>(expeditionService.getAllUserExpeditions(userId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/ride/{rideId}/all")
    public ResponseEntity<List<ExpeditionResponse>> getRideExpeditions(@PathVariable("rideId") long rideId){
        return new ResponseEntity<>(expeditionService.getRideExpeditions(rideId), HttpStatus.ACCEPTED);
    }

    @PostMapping("/create")
    public ResponseEntity<Expedition> createExpedition(@RequestParam("expeditionRequest") String requestJson,
                                                       @RequestParam("packetImage") MultipartFile packetImage){
        // constructs packet image url
        String packetImageUrl = MvcUriComponentsBuilder
                .fromMethodName(ExpeditionController.class, "serveFile", packetImage.getOriginalFilename())
                .build().toString();

        // Store the packet image
        fileStorageService.storeFile(packetImage);

        // Parse the request JSON String into a ExpeditionRequest object
        ExpeditionRequest expeditionRequest = expeditionMapper.toRideRequest(requestJson);

        // Save the expedition with the updated image URL
        return new ResponseEntity<>(expeditionService.createExpedition(expeditionRequest,packetImageUrl), HttpStatus.CREATED);
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        /**
         * Serves a file as a resource.
         */
        Resource file = fileStorageService.loadFileAsResource(filename);
        if (file == null)
            return ResponseEntity.notFound().build();

        // Set the Content-Disposition header to indicate that the file should be downloaded.
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
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

    @PutMapping("/accept/{idExp}")
    public void cancelReservation(long reservationId, long expeditionId) {
        expeditionService.cancelExxpedition(expeditionId);
    }
}
