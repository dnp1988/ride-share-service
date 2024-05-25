package com.ride.share.api;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * API model for the body of Car related outgoing responses.
 */
@Data
@AllArgsConstructor
public class CarResponse {

    /**
     * External Car ID.
     */
    private Long id;

    /**
     * Number of total seats in the Car.
     */
    private Integer seats;

    /**
     * Number of seats in the Car unassigned to any Ride.
     */
    private Integer availableSeats;
}
