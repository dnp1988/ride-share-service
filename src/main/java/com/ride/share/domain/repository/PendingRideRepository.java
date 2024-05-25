package com.ride.share.domain.repository;

import com.ride.share.domain.dto.PendingRideDTO;

import java.util.Collection;

/**
 * Handles a queue of Pending Rides using {@link PendingRideDTO}.
 */
public interface PendingRideRepository {

    /**
     * Removes the Pending Ride at the front of the queue and returns it.
     *
     * @return first Pending Ride in the queue or null if the queue is empty
     */
    PendingRideDTO poll();

    /**
     * Inserts the given Pending Ride in the queue.
     *
     * @param pendingRide Pending Ride to insert
     */
    void add(PendingRideDTO pendingRide);

    /**
     * Inserts all the given Pending Rides in the queue.
     *
     * @param pendingRides Pending Rides to store
     */
    void addAll(Collection<PendingRideDTO> pendingRides);

    /**
     * Deletes all stored Pending Rides.
     */
    void clear();
}
