package com.lameute.expedition_service;

import com.lameute.expedition_service.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing
public class ExpeditionServiceApplication {
	@Autowired
	FileStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(ExpeditionServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return (args) -> {
			storageService.init(); // We create packet images directory on app launching
		};
	}
}
