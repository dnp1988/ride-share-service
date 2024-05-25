package com.ride.share.domain.usecase;

import com.ride.share.configuration.mapper.PendingRideDTOMapper;
import com.ride.share.domain.dto.PendingRideDTO;
import com.ride.share.domain.entity.RideStatus;
import com.ride.share.domain.repository.PendingRideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Goes through the Pending Rides in the Queue and attempts to initiate them.
 * All Pending Rides that cannot be initiated are reinserted in the Queue to be revised again in the future.
 * <p>
 * A scheduled strategy is in place to trigger this logic regularly.
 */
@Component
public class RevisePendingRides {

    private static final Logger LOGGER = LoggerFactory.getLogger(RevisePendingRides.class);
    private BeginPendingRide beginPendingRide;
    private PendingRideRepository pendingRideRepository;
    private PendingRideDTOMapper pendingRideDTOMapper;

    /**
     * Creates a regular use case instance.
     *
     * @param beginPendingRide      Use Case of BeginPendingRide
     * @param pendingRideRepository Repository for Pending Rides
     * @param pendingRideDTOMapper  Mapper of Ride DTO
     */
    public RevisePendingRides(BeginPendingRide beginPendingRide,
                              PendingRideRepository pendingRideRepository,
                              PendingRideDTOMapper pendingRideDTOMapper) {
        this.beginPendingRide = beginPendingRide;
        this.pendingRideRepository = pendingRideRepository;
        this.pendingRideDTOMapper = pendingRideDTOMapper;
    }

    /**
     * Goes through the Pending Rides in the Queue and attempts to initiate them.
     * All Pending Rides that cannot be initiated are reinserted in the Queue to be revised again in the future.
     * <p>
     * A scheduled strategy is in place to run this logic regularly.
     */
    @Scheduled(fixedRateString = "${ride-share.revise-pending-rides.run-rate-millis:30000}")
    public void revise() {
        List<PendingRideDTO> retries = new ArrayList<>();

        LOGGER.info("Revising PendingRides in Queue");

        PendingRideDTO pendingRideDTO = pendingRideRepository.poll();

        //Iterate through Pending Rides in the Queue
        while (Objects.nonNull(pendingRideDTO)) {

            //Revise Pending Ride by attempting to initiate it
            RideStatus status = beginPendingRide.begin(pendingRideDTOMapper.map(pendingRideDTO));

            LOGGER.info("PendingRide({}) revision ended with status {}", pendingRideDTO.getId(), status);

            if (RideStatus.UNASSIGNED.equals(status)) {

                //If the Pending Ride remains UNASSIGNED after being revised, it is saved in the list.
                retries.add(pendingRideDTO);
            }

            pendingRideDTO = pendingRideRepository.poll();
        }

        //All UNASSIGNED Pending Rides are reinserted in the Queue to be revised again in a future Job run.
        pendingRideRepository.addAll(retries);
    }

}
