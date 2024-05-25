package com.ride.share;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main Spring Boot Class for Ride Share Application.
 */
@EnableScheduling
@SpringBootApplication
public class RideShareApplication {

    /**
     * Main method for Ride Share Application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(RideShareApplication.class, args);
    }
}
