package com.ride.share.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Data Transfer Object used to save Pending Ride information.
 */
@Data
@EqualsAndHashCode
public class PendingRideDTO {

    /**
     * External Ride ID.
     */
    private Long id;

    /**
     * Number of passengers that are part of the Ride.
     */
    private Integer passengers;

    /**
     * Timestamp in milliseconds of the time of first insertion in the Pending Ride queue.
     */
    private Long entryTime;

    /**
     * Creates a regular instance.
     * EntryTime value is initially null.
     *
     * @param id         External Ride ID.
     * @param passengers Number of total passengers in the Ride
     */
    public PendingRideDTO(Long id, Integer passengers) {
        this.id = id;
        this.passengers = passengers;
    }
}
