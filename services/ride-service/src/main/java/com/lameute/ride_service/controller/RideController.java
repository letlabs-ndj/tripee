package com.lameute.ride_service.controller;

import com.lameute.ride_service.dto.RideRequest;
import com.lameute.ride_service.dto.RideResponse;
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

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rides")
public class RideController {
    @Autowired
    private RideService rideService;

    @Autowired
    private RideMapper rideMapper;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/save")
    public ResponseEntity<Ride> saveRide(@RequestParam("rideRequest") String requestJson,
                                        @RequestParam("vehicleImage") MultipartFile vehicleImage) throws SocketException, UnknownHostException {
        System.out.println(requestJson);
        String imageUrl = "";
        if (vehicleImage != null){
            // constructs vehicle image url
            imageUrl = MvcUriComponentsBuilder
                    .fromMethodName(RideController.class, "serveFile", vehicleImage.getOriginalFilename())
                    .build().toString();

            /* We get server's ip address */
            String ip = "";
            try(final DatagramSocket socket = new DatagramSocket()){
                /* Dummy connection to trigger the socket's initialization
                 and assign a local port and IP address */
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);

                // Get server's ip address
                ip = socket.getLocalAddress().getHostAddress();
            }

            /* we replace localhost by the ip address in the image url so as to be
             able to acces the image */
            imageUrl = imageUrl.replaceFirst("localhost", ip);

            // We store the vehicle image
            fileStorageService.storeFile(vehicleImage);
        }

        // Parse the request JSON String into a RideRequest object
        RideRequest rideRequest = rideMapper.toRideRequest(requestJson);

        // Save the ride with the image URL
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

    @GetMapping("/id/{idRide}")
    public ResponseEntity<RideResponse> getRideById(@PathVariable("idRide") long idRide){
        return new ResponseEntity<>(rideService.getRideById(idRide), HttpStatus.OK);
    }

    @PutMapping("/update/{idRide}")
    public Ride updateRide(@RequestBody RideRequest rideRequest,
                                 @PathVariable("idRide") long idRide){
        return rideService.updateRide(rideRequest,idRide);
    }

    @GetMapping("/user/{idUser}/all")
    public List<RideResponse> getAllUserRides(@PathVariable("idUser") long idUser){
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
    public ResponseEntity<List<RideResponse>> searchRides(@RequestParam("departurePlace") String departurePlace,
                                                          @RequestParam("arrivalPlace") String arrivalPlace){
        return new ResponseEntity<>(rideService.searchRide(departurePlace, arrivalPlace), HttpStatus.OK);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> checkUser(@PathVariable("id") long id) {
        return new ResponseEntity<>(rideService.existById(id), HttpStatus.OK);
    }

    @PutMapping("/id/{idRide}/availablePlaces/restore/{numberOfPlaces}")
    public void restoreAvailablePlaces(@PathVariable("idRide") long idRide,
                                       @PathVariable("numberOfPlaces") int numberOfPlaces) {
        rideService.restoreAvailablePlaces(idRide,numberOfPlaces);
    }

    @PutMapping("/id/{idRide}/availablePlaces/remove/{numberOfPlaces}")
    public void removeAvailablePlaces(@PathVariable("idRide") long idRide,
                                       @PathVariable("numberOfPlaces") int numberOfPlaces) {
        rideService.removeAvailablePlaces(idRide,numberOfPlaces);
    }
}
