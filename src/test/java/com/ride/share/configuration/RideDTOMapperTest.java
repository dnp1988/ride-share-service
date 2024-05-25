package com.ride.share.configuration;

import com.ride.share.configuration.mapper.RideDTOMapper;
import com.ride.share.domain.dto.RideDTO;
import com.ride.share.domain.entity.Ride;
import org.junit.jupiter.api.Test;

import static com.ride.share.tools.TestConstants.RIDE_ID_1;
import static com.ride.share.tools.TestConstants.RIDE_PEOPLE_1;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RideDTOMapperTest {

    private RideDTOMapper rideDTOMapper = new RideDTOMapper();

    @Test
    public void testMapperRideToRideDTO() {
        assertEquals(
                new RideDTO(RIDE_ID_1, RIDE_PEOPLE_1),
                rideDTOMapper.map(new Ride(RIDE_ID_1, RIDE_PEOPLE_1))
        );
    }

    @Test
    public void testMapperRideDTOToRide() {
        assertEquals(
                new Ride(RIDE_ID_1, RIDE_PEOPLE_1),
                rideDTOMapper.map(new RideDTO(RIDE_ID_1, RIDE_PEOPLE_1))
        );
    }
}
