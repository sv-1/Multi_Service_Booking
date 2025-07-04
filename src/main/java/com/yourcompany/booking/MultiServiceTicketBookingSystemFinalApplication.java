package com.yourcompany.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class MultiServiceTicketBookingSystemFinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiServiceTicketBookingSystemFinalApplication.class, args);
		System.out.println("Multi-Service Ticket Booking System is up and running...");
	}

}
