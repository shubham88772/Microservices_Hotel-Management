package com.practice.rating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class RatingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatingServiceApplication.class, args);
	}

}
