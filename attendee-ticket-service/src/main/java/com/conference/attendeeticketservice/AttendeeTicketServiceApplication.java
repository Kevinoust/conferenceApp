package com.conference.attendeeticketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AttendeeTicketServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttendeeTicketServiceApplication.class, args);
	}

}
