package com.ride.share.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Main model for Ride information used in the Use Cases.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Ride {

    /**
     * External ID of Ride
     */
    private Long id;

    /**
     * Number of passengers that are part of the Ride.
     */
    private Integer passengers;

    /**
     * Car to which the Ride is currently assigned.
     */
    @Setter
    private Car assignedTo;

    /**
     * Creates a regular Instance.
     * AssignedTo value is initially null.
     *
     * @param id         External Ride ID
     * @param passengers Number of total passengers in the Ride
     */
    public Ride(final Long id, final Integer passengers) {
        this.id = id;
        this.passengers = passengers;
        this.assignedTo = null;
    }
}
