package com.ride.share.api;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * API model for the body of Car related incoming requests.
 */
@Data
@AllArgsConstructor
public class CarRequest {

    /**
     * External Car ID.
     */
    private Long id;

    /**
     * Number of total seats in the Car.
     */
    private Integer seats;
}
