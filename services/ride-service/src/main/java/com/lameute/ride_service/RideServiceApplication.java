package com.lameute.ride_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.lameute.ride_service.service.FileStorageService;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing
public class RideServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RideServiceApplication.class, args);
	}
	@Autowired
	FileStorageService storageService;
	
	@Bean
	CommandLineRunner init() {
		return (args) -> {
			storageService.init(); // We create vehicle images directory on app launching
		};
	}
}
