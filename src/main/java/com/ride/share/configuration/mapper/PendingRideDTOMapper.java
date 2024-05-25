package com.ride.share.configuration.mapper;

import com.ride.share.domain.dto.PendingRideDTO;
import com.ride.share.domain.entity.Ride;
import com.ride.share.domain.entity.PendingRide;
import org.springframework.stereotype.Component;

import java.util.Objects;


/**
 * Maps values between {@link PendingRideDTO} and other Ride related models.
 */
@Component
public class PendingRideDTOMapper {

    /**
     * Maps a {@link PendingRide} to a {@link PendingRideDTO}.
     *
     * @param pendingRide Pending Ride to map
     * @return mapped DTO Pending Ride
     */
    public PendingRideDTO map(PendingRide pendingRide) {
        if (Objects.isNull(pendingRide)) {
            return null;
        } else {
            return new PendingRideDTO(pendingRide.getId(), pendingRide.getPassengers());
        }
    }

    /**
     * Maps a {@link PendingRideDTO} to a {@link PendingRide}.
     *
     * @param pendingRideDTO DTO Pending Ride to map
     * @return mapped Pending Ride
     */
    public PendingRide map(PendingRideDTO pendingRideDTO) {
        if (Objects.isNull(pendingRideDTO)) {
            return null;
        } else {
            return new PendingRide(pendingRideDTO.getId(), pendingRideDTO.getPassengers());
        }
    }

    /**
     * Maps a {@link Ride} to a {@link PendingRideDTO}.
     *
     * @param ride Ride to map
     * @return mapped DTO Pending Ride
     */
    public PendingRideDTO map(Ride ride) {
        if (Objects.isNull(ride)) {
            return null;
        } else {
            return new PendingRideDTO(ride.getId(), ride.getPassengers());
        }
    }
}
