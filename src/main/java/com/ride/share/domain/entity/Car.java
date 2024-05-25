package com.ride.share.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Main model for Car information used in the Use Cases.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Car {

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
    public Car(final Long id, final Integer seats) {
        this.id = id;
        this.maxSeats = seats;
        this.availableSeats = seats;
    }
}
