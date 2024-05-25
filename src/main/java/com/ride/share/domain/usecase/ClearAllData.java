package com.ride.share.domain.usecase;

import com.ride.share.domain.repository.CarRepository;
import com.ride.share.domain.repository.RideRepository;
import com.ride.share.domain.repository.PendingRideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Removes all existing Rides, Pending Rides and Cars from their Repositories.
 */
@Component
public class ClearAllData {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClearAllData.class);

    private CarRepository carRepository;
    private RideRepository rideRepository;
    private PendingRideRepository pendingRideRepository;

    /**
     * Creates a regular use case instance.
     *
     * @param carRepository            Repository for Cars
     * @param rideRepository        Repository for Rides
     * @param pendingRideRepository Repository for Pending Rides
     */
    public ClearAllData(CarRepository carRepository,
                        RideRepository rideRepository,
                        PendingRideRepository pendingRideRepository) {
        this.carRepository = carRepository;
        this.rideRepository = rideRepository;
        this.pendingRideRepository = pendingRideRepository;
    }


    /**
     * Removes all existing Rides, Pending Rides and Cars from their Repositories.
     */
    public void clear() {
        LOGGER.info("Clearing All Data");

        carRepository.clear();
        rideRepository.clear();
        pendingRideRepository.clear();
    }
}