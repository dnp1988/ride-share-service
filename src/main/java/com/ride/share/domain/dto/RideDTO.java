package com.ride.share.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object used to save Ride information.
 */
@Data
@AllArgsConstructor
public class RideDTO {

    /**
     * External Ride ID.
     */
    private Long id;

    /**
     * Number of passengers that are part of the Ride.
     */
    private Integer passengers;

    /**
     * ID of the Car to which the Ride is currently assigned.
     */
    private Long assignedToCarId;

    /**
     * Creates a regular Instance.
     * AssignedToCarId value is initially null.
     *
     * @param id         External Ride ID
     * @param passengers Number of total passengers in the Ride
     */
    public RideDTO(final Long id, final Integer passengers) {
        this.id = id;
        this.passengers = passengers;
        this.assignedToCarId = null;
    }
}
