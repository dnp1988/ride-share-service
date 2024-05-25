package com.ride.share.domain.usecase;

import com.ride.share.configuration.mapper.PendingRideDTOMapper;
import com.ride.share.domain.entity.Ride;
import com.ride.share.domain.repository.PendingRideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Saves a new Ride in the Pending Rides Queue through its Repository.
 */
@Component
public class QueueNewRide {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueNewRide.class);

    private PendingRideRepository pendingRideRepository;
    private PendingRideDTOMapper pendingRideDTOMapper;

    /**
     * Creates a regular use case instance.
     *
     * @param pendingRideRepository Repository for Pending Rides
     * @param pendingRideDTOMapper  Mapper of Pending Ride DTO
     */
    public QueueNewRide(PendingRideRepository pendingRideRepository,
                        PendingRideDTOMapper pendingRideDTOMapper) {
        this.pendingRideRepository = pendingRideRepository;
        this.pendingRideDTOMapper = pendingRideDTOMapper;
    }

    /**
     * Saves a new Ride in the Pending Rides Queue through its Repository.
     *
     * @param ride New Ride to be queued
     */
    public void queue(Ride ride) {
        LOGGER.info("Queuing New Ride({})", ride.getId());

        pendingRideRepository.add(pendingRideDTOMapper.map(ride));
    }
}
