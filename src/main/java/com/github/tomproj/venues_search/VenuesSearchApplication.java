package com.github.tomproj.venues_search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableFeignClients
public class VenuesSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(VenuesSearchApplication.class, args);
	}

}

