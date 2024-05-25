package com.ride.share.domain.repository;

import com.ride.share.domain.dto.CarDTO;

import java.util.Collection;

/**
 * Handles storage of Cars using {@link CarDTO} and Car seat locking.
 */
public interface CarRepository {

    /**
     * Fetches Car with given ID.
     *
     * @param id ID of Car to fetch
     * @return Fetched Car or null if the ID cannot be found
     */
    CarDTO get(Long id);

    /**
     * Stores the given Car indexed by its ID.
     *
     * @param car Car to store
     */
    void save(CarDTO car);

    /**
     * Stores all the given Cars contained in the collection.
     *
     * @param cars Cars to store
     */
    void saveAll(Collection<CarDTO> cars);

    /**
     * Locks a given number of seats from any already stored Car that has enough sets available.
     *
     * @param seats number of seats to lock
     * @return Car with locked seats or null if no suitable Car is found
     */
    CarDTO takeSeats(Integer seats);

    /**
     * Unlocks a given number of seats from the Car with the given ID.
     *
     * @param id
     * @param seats number of seats to unlock
     * @return Car with unlocked seats or null if the ID cannot be found
     */
    CarDTO releaseSeats(Long id, Integer seats);

    /**
     * Deletes all stored Cars.
     */
    void clear();
}
