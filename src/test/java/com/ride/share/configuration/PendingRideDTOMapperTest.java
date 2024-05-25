package com.ride.share.configuration;

import com.ride.share.configuration.mapper.PendingRideDTOMapper;
import com.ride.share.domain.dto.PendingRideDTO;
import com.ride.share.domain.entity.Ride;
import com.ride.share.domain.entity.PendingRide;
import org.junit.jupiter.api.Test;

import static com.ride.share.tools.TestConstants.RIDE_ID_1;
import static com.ride.share.tools.TestConstants.RIDE_PEOPLE_1;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PendingRideDTOMapperTest {

    private PendingRideDTOMapper pendingRideDTOMapper = new PendingRideDTOMapper();

    @Test
    public void testMapperRideToPendingRideDTO() {
        assertEquals(
                new PendingRideDTO(RIDE_ID_1, RIDE_PEOPLE_1),
                pendingRideDTOMapper.map(new Ride(RIDE_ID_1, RIDE_PEOPLE_1))
        );
    }

    @Test
    public void testMapperPendingRideToPendingRideDTO() {
        assertEquals(
                new PendingRideDTO(RIDE_ID_1, RIDE_PEOPLE_1),
                pendingRideDTOMapper.map(new PendingRide(RIDE_ID_1, RIDE_PEOPLE_1))
        );
    }

    @Test
    public void testMapperPendingRideDTOToPendingRide() {
        assertEquals(
                new PendingRide(RIDE_ID_1, RIDE_PEOPLE_1),
                pendingRideDTOMapper.map(new PendingRideDTO(RIDE_ID_1, RIDE_PEOPLE_1))
        );
    }
}
