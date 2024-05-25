package com.ride.share.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


/**
 * Main model for Pending Ride information used in the Use Cases.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PendingRide {

    /**
     * External Ride ID.
     */
    private Long id;

    /**
     * Number of passengers that are part of the Ride.
     */
    private Integer passengers;
}
