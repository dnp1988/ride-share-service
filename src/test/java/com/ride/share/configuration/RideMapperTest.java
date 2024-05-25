package com.ride.share.configuration;

import com.ride.share.api.RideRequest;
import com.ride.share.configuration.mapper.RideMapper;
import com.ride.share.domain.entity.Ride;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RideMapperTest {

    private static final Long RIDE_ID = 1L;
    private static final Integer RIDE_PEOPLE = 4;

    private RideMapper rideMapper = new RideMapper();

    @Test
    public void testMapperCarRequestToCar() {
        assertEquals(
                new Ride(RIDE_ID, RIDE_PEOPLE),
                rideMapper.map(new RideRequest(RIDE_ID, RIDE_PEOPLE))
        );
    }
}
