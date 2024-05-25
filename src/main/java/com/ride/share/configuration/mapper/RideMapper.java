package com.ride.share.configuration.mapper;

import com.ride.share.api.RideRequest;
import com.ride.share.domain.entity.Ride;
import org.springframework.stereotype.Component;

import java.util.Objects;


/**
 * Maps values between {@link Ride} and other Ride API related models.
 */
@Component
public class RideMapper {

    /**
     * Maps a {@link RideRequest} to a {@link Ride}.
     *
     * @param rideRequest
     * @return
     */
    public Ride map(RideRequest rideRequest) {
        if (Objects.isNull(rideRequest)) {
            return null;
        } else {
            return new Ride(rideRequest.getId(), rideRequest.getPeople());
        }
    }
}
