package com.api.busTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BusTimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusTimeApplication.class, args);
		
		
	}
	
}
