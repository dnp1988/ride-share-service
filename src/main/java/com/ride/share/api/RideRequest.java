package com.ride.share.api;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * API model for the body of Ride related incoming requests.
 */
@Data
@AllArgsConstructor
public class RideRequest {

    /**
     * External Ride ID.
     */
    private Long id;

    /**
     * Number of passengers that are part of the Ride.
     */
    private Integer people;
}
