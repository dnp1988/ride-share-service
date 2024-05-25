package com.ride.share.domain.entity;

import com.ride.share.domain.usecase.BeginPendingRide;


/**
 * Result values coming from the revision of a {@link PendingRide} in {@link BeginPendingRide}.
 */
public enum RideStatus {

    /**
     * Result indicating that a Pending Ride has been assigned to a Car.
     */
    ASSIGNED,

    /**
     * Result indicating that a Pending Ride has been cancelled before being assigned to a Car.
     */
    CANCELLED,

    /**
     * Result indicating that a Pending Ride could not be assigned to a Car and is still pending.
     */
    UNASSIGNED
}
