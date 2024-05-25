package com.ride.share.domain.usecase;

import com.ride.share.domain.dto.CarDTO;
import com.ride.share.domain.entity.Car;
import com.ride.share.domain.entity.Ride;
import com.ride.share.domain.repository.CarRepository;
import com.ride.share.domain.repository.RideRepository;
import org.junit.jupiter.api.Test;

import static com.ride.share.tools.TestConstants.CAR_ID_1;
import static com.ride.share.tools.TestConstants.CAR_SEATS_1;
import static com.ride.share.tools.TestConstants.RIDE_ID_1;
import static com.ride.share.tools.TestConstants.RIDE_PEOPLE_1;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class EndRideTest {

    private RetrieveRide retrieveRide = mock(RetrieveRide.class);
    private CarRepository carRepository = mock(CarRepository.class);
    private RideRepository rideRepository = mock(RideRepository.class);

    private EndRide useCase = new EndRide(retrieveRide, rideRepository, carRepository);

    @Test
    public void testEndRideTest() {
        Car car = new Car(CAR_ID_1, CAR_SEATS_1, 0);
        Ride ride = new Ride(RIDE_ID_1, RIDE_PEOPLE_1, car);
        when(retrieveRide.retrieve(RIDE_ID_1)).thenReturn(ride);
        when(carRepository.releaseSeats(CAR_ID_1, RIDE_PEOPLE_1)).thenReturn(mock(CarDTO.class));

        useCase.end(RIDE_ID_1);

        verify(retrieveRide).retrieve(RIDE_ID_1);
        verify(carRepository).releaseSeats(RIDE_ID_1, RIDE_PEOPLE_1);
        verify(rideRepository).remove(RIDE_ID_1);
    }

    @Test
    public void testEndCancelledRideTest() {
        Ride ride = new Ride(RIDE_ID_1, RIDE_PEOPLE_1);
        when(retrieveRide.retrieve(RIDE_ID_1)).thenReturn(ride);

        useCase.end(RIDE_ID_1);

        verify(retrieveRide).retrieve(RIDE_ID_1);
        verifyNoInteractions(carRepository);
        verify(rideRepository).remove(RIDE_ID_1);
    }
}
