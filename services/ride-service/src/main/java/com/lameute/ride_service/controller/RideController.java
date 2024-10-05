package com.lameute.ride_service.controller;

import com.lameute.ride_service.dto.RideRequest;
import com.lameute.ride_service.model.Ride;
import com.lameute.ride_service.service.FileStorageService;
import com.lameute.ride_service.service.RideService;
import com.lameute.ride_service.service.RideMapper;

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
@RequestMapping("/ride")
public class RideController {
    @Autowired
    private RideService rideService;

    @Autowired
    private RideMapper rideMapper;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/save")
    public ResponseEntity<Ride> saveRide(@RequestParam("rideRequest") String requestJson,
                                        @RequestParam("vehicleImage") MultipartFile vehicleImage){

        // constructs vehicle image url
        String imageUrl = MvcUriComponentsBuilder
				.fromMethodName(RideController.class, "serveFile", vehicleImage.getOriginalFilename())
				.build().toString();

        // Store the vehicle image and get the URL
        fileStorageService.storeFile(vehicleImage);

        // Parse the request JSON String into a RideRequest object
        RideRequest rideRequest = rideMapper.toRideRequest(requestJson);

        // Save the ride with the updated image URL
        return new ResponseEntity<>(rideService.saveRide(rideRequest,imageUrl), HttpStatus.CREATED);
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
    
    @PutMapping("/update/{idRide}")
    public Ride updateRide(@RequestBody RideRequest rideRequest,
                                 @PathVariable("idRide") long idRide){
        return rideService.updateRide(rideRequest,idRide);
    }

    @GetMapping("/all/{idUser}")
    public List<Ride> getAllUserRides(@PathVariable("idUser") long idUser){
        return rideService.getAllRidesByUserId(idUser);
    }

    @DeleteMapping("/delete/{idRide}")
    public void deleteRide(@PathVariable("idRide") long idRide){
        rideService.deleteRide(idRide);
    }

    @PutMapping("/startRide/{idRide}")
    public void startRide(@PathVariable("idRide") long idRide){
        rideService.startRide(idRide);
    }

    @PutMapping("/terminateRide/{idRide}")
    public void terminateRide(@PathVariable("idRide") long idRide){
        rideService.terminateRide(idRide);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Ride>> searchRides(@RequestParam("departurePlace") String departurePlace,
                                                    @RequestParam("arrivalPlace") String arrivalPlace){
        return new ResponseEntity<>(rideService.searchRide(departurePlace, arrivalPlace), HttpStatus.OK);
    }
}
