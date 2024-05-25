package com.ride.share.domain.repository;

import com.ride.share.domain.dto.RideDTO;

import java.util.Collection;


/**
 * Handles storage of Rides using {@link RideDTO}.
 */
public interface RideRepository {

    /**
     * Fetches Ride with given ID.
     *
     * @param id ID of Ride to fetch
     * @return Fetched Ride or null if the ID cannot be found
     */
    RideDTO get(Long id);

    /**
     * Stores the given Ride indexed by its ID.
     *
     * @param ride Ride to store
     */
    void save(RideDTO ride);

    /**
     * Stores all the given Rides contained in the collection.
     *
     * @param rides Rides to store
     */
    void saveAll(Collection<RideDTO> rides);

    /**
     * Assigns a Car with given {@code carId} to a Ride with given {@code rideId}.
     * Locates the already stored Ride and updates its Car assignment reference.
     *
     * @param rideId    ID of the Ride to which assign the Car
     * @param carId     ID of the Car to be assigned
     * @return
     */
    RideDTO updateAssignment(Long rideId, Long carId);

    /**
     * Deletes the Ride with given ID.
     *
     * @param id ID of Ride to delete
     */
    void remove(Long id);

    /**
     * Deletes all stored Rides.
     */
    void clear();
}
