package com.ride.share.configuration.mapper;

import com.ride.share.domain.dto.RideDTO;
import com.ride.share.domain.entity.Ride;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Maps values between {@link RideDTO} and other Ride related models.
 */
@Component
public class RideDTOMapper {

    /**
     * Maps a {@link Ride} to a {@link RideDTO}.
     *
     * @param ride Ride to map
     * @return mapped DTO Ride
     */
    public RideDTO map(Ride ride) {
        if (Objects.isNull(ride)) {
            return null;
        } else {
            return new RideDTO(ride.getId(), ride.getPassengers());
        }
    }

    /**
     * Maps a {@link RideDTO} to a {@link Ride}.
     *
     * @param rideDTO DTO Ride to map
     * @return mapped Ride
     */
    public Ride map(RideDTO rideDTO) {
        if (Objects.isNull(rideDTO)) {
            return null;
        } else {
            return new Ride(rideDTO.getId(), rideDTO.getPassengers());
        }
    }
}
