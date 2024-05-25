package com.ride.share.domain.usecase;

import com.ride.share.configuration.mapper.RideDTOMapper;
import com.ride.share.domain.dto.RideDTO;
import com.ride.share.domain.entity.Ride;
import com.ride.share.domain.repository.RideRepository;
import com.google.common.base.VerifyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.ride.share.tools.TestConstants.RIDE_ID_1;
import static com.ride.share.tools.TestConstants.RIDE_PEOPLE_1;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

public class CreateRideTest {

    private RideRepository rideRepository = mock(RideRepository.class);

    private CreateRide useCase = new CreateRide(rideRepository, new RideDTOMapper());

    @Test
    public void testCreateRideTest() {
        Ride ride = new Ride(RIDE_ID_1, RIDE_PEOPLE_1);
        RideDTO rideDTO = new RideDTO(RIDE_ID_1, RIDE_PEOPLE_1);

        useCase.create(ride);

        verify(rideRepository).save(rideDTO);
    }

    @ParameterizedTest
    @MethodSource("testCreateRideVerifyErrorParams")
    public void testCreateRideVerifyError(Ride ride) {
        assertThrowsExactly(VerifyException.class, () -> useCase.create(ride));
        verifyNoInteractions(rideRepository);
    }

    private static Stream<Ride> testCreateRideVerifyErrorParams() {
        return Stream.of(
                null,
                new Ride(null, RIDE_PEOPLE_1),
                new Ride(0L, RIDE_PEOPLE_1),
                new Ride(RIDE_ID_1, 7),
                new Ride(RIDE_ID_1, 0)
        );
    }
}
