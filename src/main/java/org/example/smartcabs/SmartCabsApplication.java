package org.example.smartcabs;

import org.example.smartcabs.model.Location;
import org.example.smartcabs.service.LocationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class SmartCabsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartCabsApplication.class, args);
	}

	@Bean
	CommandLineRunner run(LocationService locationService) {
		return args ->{
			locationService.populateGraph();
			locationService.populateCab();
		};
	}
}
