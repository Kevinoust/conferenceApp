package com.conference.speakerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpeakerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpeakerServiceApplication.class, args);
	}

}
