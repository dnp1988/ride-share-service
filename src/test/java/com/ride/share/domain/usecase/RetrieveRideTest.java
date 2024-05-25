package com.ride.share.domain.usecase;

import com.ride.share.configuration.mapper.RideDTOMapper;
import com.ride.share.domain.dto.RideDTO;
import com.ride.share.domain.entity.Car;
import com.ride.share.domain.entity.Ride;
import com.ride.share.domain.repository.RideRepository;
import com.google.common.base.VerifyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static com.ride.share.tools.TestConstants.CAR_ID_1;
import static com.ride.share.tools.TestConstants.CAR_SEATS_1;
import static com.ride.share.tools.TestConstants.RIDE_ID_1;
import static com.ride.share.tools.TestConstants.RIDE_PEOPLE_1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class RetrieveRideTest {

    private RetrieveCar retrieveCar = mock(RetrieveCar.class);
    private RideRepository rideRepository = mock(RideRepository.class);

    private RetrieveRide useCase = new RetrieveRide(retrieveCar, rideRepository, new RideDTOMapper());

    @Test
    public void testRetrieveRide() {
        RideDTO rideDTO = new RideDTO(RIDE_ID_1, RIDE_PEOPLE_1, CAR_ID_1);
        Car carEntity = new Car(CAR_ID_1, CAR_SEATS_1, 0);
        Ride rideEntity = new Ride(RIDE_ID_1, RIDE_PEOPLE_1, carEntity);

        when(rideRepository.get(RIDE_ID_1)).thenReturn(rideDTO);
        when(retrieveCar.retrieve(CAR_ID_1)).thenReturn(carEntity);

        Ride result = useCase.retrieve(RIDE_ID_1);
        assertEquals(rideEntity, result);

        verify(rideRepository).get(RIDE_ID_1);
        verify(retrieveCar).retrieve(CAR_ID_1);
    }

    @Test
    public void testRetrieveUnassignedRide() {
        RideDTO rideDTO = new RideDTO(RIDE_ID_1, RIDE_PEOPLE_1);
        Ride rideEntity = new Ride(RIDE_ID_1, RIDE_PEOPLE_1);

        when(rideRepository.get(RIDE_ID_1)).thenReturn(rideDTO);

        Ride result = useCase.retrieve(RIDE_ID_1);
        assertEquals(rideEntity, result);

        verify(rideRepository).get(RIDE_ID_1);
        verifyNoInteractions(retrieveCar);
    }

    @Test
    public void testRetrieveRideNotFound() {
        assertThrowsExactly(NoSuchElementException.class, () -> useCase.retrieve(RIDE_ID_1));
        verify(rideRepository).get(RIDE_ID_1);
        verifyNoInteractions(retrieveCar);
    }

    @ParameterizedTest
    @MethodSource("testRetrieveRideVerifyErrorParams")
    public void testRetrieveRideVerifyError(Long rideId) {
        assertThrowsExactly(VerifyException.class, () -> useCase.retrieve(rideId));
        verifyNoInteractions(rideRepository);
        verifyNoInteractions(retrieveCar);
    }

    private static Stream<Long> testRetrieveRideVerifyErrorParams() {
        return Stream.of(0L, null);
    }
}
