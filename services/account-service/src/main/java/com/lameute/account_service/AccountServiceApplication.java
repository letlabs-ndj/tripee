package com.lameute.account_service;

import com.lameute.account_service.dto.UserRequest;
import com.lameute.account_service.service.AuthenticationService;
import com.lameute.account_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class
AccountServiceApplication {
	@Autowired
	AuthenticationService authenticationService;

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner initUserDb(){
			return (args) -> {
				if (userService.isEmpty()) {
					authenticationService.registerUser(
							new UserRequest(
									"tripee",
									"tripee@gmail.com",
									"password",
									"123456789"
							)
					);
				}
			};
	}

}