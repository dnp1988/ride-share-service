package com.ride.share.domain.usecase;

import com.ride.share.configuration.mapper.PendingRideDTOMapper;
import com.ride.share.domain.dto.PendingRideDTO;
import com.ride.share.domain.entity.Ride;
import com.ride.share.domain.repository.PendingRideRepository;
import org.junit.jupiter.api.Test;

import static com.ride.share.tools.TestConstants.RIDE_ID_1;
import static com.ride.share.tools.TestConstants.RIDE_PEOPLE_1;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class QueueNewRideTest {

    private PendingRideRepository pendingRideRepository = mock(PendingRideRepository.class);

    private QueueNewRide useCase = new QueueNewRide(pendingRideRepository, new PendingRideDTOMapper());

    @Test
    public void testQueueRide() {
        Ride ride = new Ride(RIDE_ID_1, RIDE_PEOPLE_1);
        PendingRideDTO pendingRideDTO = new PendingRideDTO(RIDE_ID_1, RIDE_PEOPLE_1);

        useCase.queue(ride);

        verify(pendingRideRepository).add(pendingRideDTO);
    }
}
