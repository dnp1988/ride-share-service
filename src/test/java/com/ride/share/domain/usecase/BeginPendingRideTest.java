package com.ride.share.domain.usecase;

import com.ride.share.domain.dto.CarDTO;
import com.ride.share.domain.dto.RideDTO;
import com.ride.share.domain.entity.RideStatus;
import com.ride.share.domain.entity.PendingRide;
import com.ride.share.domain.repository.CarRepository;
import com.ride.share.domain.repository.RideRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ride.share.tools.TestConstants.CAR_ID_1;
import static com.ride.share.tools.TestConstants.CAR_SEATS_1;
import static com.ride.share.tools.TestConstants.RIDE_ID_1;
import static com.ride.share.tools.TestConstants.RIDE_PEOPLE_1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;


public class BeginPendingRideTest {

    private CarRepository carRepository = mock(CarRepository.class);
    private RideRepository rideRepository = mock(RideRepository.class);

    private BeginPendingRide useCase = new BeginPendingRide(rideRepository, carRepository);

    @Test
    @DisplayName("PendingRide is correctly assigned to a Car with available seats")
    public void testBeginPendingRideAssignedToCar() {
        PendingRide pendingRide = new PendingRide(RIDE_ID_1, RIDE_PEOPLE_1);
        CarDTO carDTO = new CarDTO(CAR_ID_1, CAR_SEATS_1, CAR_SEATS_1 - RIDE_PEOPLE_1);
        RideDTO rideDTO = new RideDTO(RIDE_ID_1, RIDE_PEOPLE_1, CAR_ID_1);

        when(carRepository.takeSeats(RIDE_PEOPLE_1)).thenReturn(carDTO);
        when(rideRepository.updateAssignment(RIDE_ID_1, CAR_ID_1)).thenReturn(rideDTO);

        assertEquals(RideStatus.ASSIGNED, useCase.begin(pendingRide));

        verify(carRepository).takeSeats(RIDE_PEOPLE_1);
        verify(rideRepository).updateAssignment(RIDE_ID_1, CAR_ID_1);
    }

    @Test
    @DisplayName("PendingRide cannot be assigned to a Car with enough seats")
    public void testBeginPendingRideUnassignedToCar() {
        PendingRide pendingRide = new PendingRide(RIDE_ID_1, RIDE_PEOPLE_1);

        when(carRepository.takeSeats(RIDE_PEOPLE_1)).thenReturn(null);

        assertEquals(RideStatus.UNASSIGNED, useCase.begin(pendingRide));

        verify(carRepository).takeSeats(RIDE_PEOPLE_1);
        verifyNoInteractions(rideRepository);
    }

    @Test
    @DisplayName("PendingRide cannot be assigned since it is identified to have been cancelled")
    public void testBeginPendingRideCancelled() {
        PendingRide pendingRide = new PendingRide(RIDE_ID_1, RIDE_PEOPLE_1);
        CarDTO carDTO = new CarDTO(CAR_ID_1, CAR_SEATS_1, CAR_SEATS_1 - RIDE_PEOPLE_1);

        when(carRepository.takeSeats(RIDE_PEOPLE_1)).thenReturn(carDTO);
        when(rideRepository.updateAssignment(RIDE_ID_1, CAR_ID_1)).thenReturn(null);
        when(carRepository.releaseSeats(RIDE_ID_1, RIDE_PEOPLE_1)).thenReturn(carDTO);

        assertEquals(RideStatus.CANCELLED, useCase.begin(pendingRide));

        verify(carRepository).takeSeats(RIDE_PEOPLE_1);
        verify(rideRepository).updateAssignment(RIDE_ID_1, CAR_ID_1);
        verify(carRepository).releaseSeats(RIDE_ID_1, RIDE_PEOPLE_1);
    }
}
