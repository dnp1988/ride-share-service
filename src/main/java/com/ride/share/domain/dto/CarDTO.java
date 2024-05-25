package com.ride.share.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object used to save Car information.
 */
@Data
@AllArgsConstructor
public class CarDTO {

    /**
     * External ID of the Car.
     */
    private Long id;

    /**
     * Number of total seats in the Car.
     */
    private Integer maxSeats;

    /**
     * Number of seats in the Car unassigned to any Ride.
     */
    private Integer availableSeats;

    /**
     * Creates a regular instance.
     * AvailableSeats value is initially the same as {@code seats}.
     *
     * @param id    External Car ID
     * @param seats Number of total seats in the Car
     */
    public CarDTO(final Long id, final Integer seats) {
        this.id = id;
        this.maxSeats = seats;
        this.availableSeats = seats;
    }
}
