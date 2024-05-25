package com.ride.share.domain.usecase;

import com.ride.share.domain.repository.CarRepository;
import com.ride.share.domain.repository.RideRepository;
import com.ride.share.domain.repository.PendingRideRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ClearAllDataTest {

    private CarRepository carRepository = mock(CarRepository.class);
    private RideRepository rideRepository = mock(RideRepository.class);
    private PendingRideRepository pendingRideRepository = mock(PendingRideRepository.class);

    private ClearAllData useCase = new ClearAllData(carRepository, rideRepository, pendingRideRepository);

    @Test
    public void testClearAllData() {
        useCase.clear();
        verify(carRepository).clear();
        verify(rideRepository).clear();
        verify(pendingRideRepository).clear();
    }

}
