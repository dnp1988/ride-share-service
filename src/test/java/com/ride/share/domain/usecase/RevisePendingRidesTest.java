package com.ride.share.domain.usecase;

import com.ride.share.configuration.mapper.PendingRideDTOMapper;
import com.ride.share.domain.dto.PendingRideDTO;
import com.ride.share.domain.entity.RideStatus;
import com.ride.share.domain.entity.PendingRide;
import com.ride.share.domain.repository.PendingRideRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ride.share.tools.TestConstants.RIDE_ID_1;
import static com.ride.share.tools.TestConstants.RIDE_ID_2;
import static com.ride.share.tools.TestConstants.RIDE_PEOPLE_1;
import static com.ride.share.tools.TestConstants.RIDE_PEOPLE_2;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RevisePendingRidesTest {

    private BeginPendingRide beginPendingRide = mock(BeginPendingRide.class);
    private PendingRideRepository pendingRideRepository = mock(PendingRideRepository.class);

    private RevisePendingRides useCase = new RevisePendingRides(beginPendingRide,
            pendingRideRepository,
            new PendingRideDTOMapper());

    @Test
    public void testRevisePendingRideNoRetries() {
        PendingRideDTO pendingRideDTO = new PendingRideDTO(RIDE_ID_1, RIDE_PEOPLE_1);
        PendingRide pendingRide = new PendingRide(RIDE_ID_1, RIDE_PEOPLE_1);

        when(pendingRideRepository.poll()).thenReturn(pendingRideDTO).thenReturn(null);
        when(beginPendingRide.begin(pendingRide)).thenReturn(RideStatus.ASSIGNED);

        useCase.revise();

        verify(pendingRideRepository, times(2)).poll();
        verify(beginPendingRide).begin(pendingRide);
        verify(pendingRideRepository).addAll(List.of());
    }

    @Test
    public void testRevisePendingRideWithRetries() {
        PendingRideDTO pendingRideDTO1 = new PendingRideDTO(RIDE_ID_1, RIDE_PEOPLE_1);
        PendingRide pendingRide1 = new PendingRide(RIDE_ID_1, RIDE_PEOPLE_1);
        PendingRideDTO pendingRideDTO2 = new PendingRideDTO(RIDE_ID_2, RIDE_PEOPLE_2);
        PendingRide pendingRide2 = new PendingRide(RIDE_ID_2, RIDE_PEOPLE_2);

        when(pendingRideRepository.poll())
                .thenReturn(pendingRideDTO1)
                .thenReturn(pendingRideDTO2)
                .thenReturn(null);
        when(beginPendingRide.begin(pendingRide1))
                .thenReturn(RideStatus.UNASSIGNED);
        when(beginPendingRide.begin(pendingRide2))
                .thenReturn(RideStatus.ASSIGNED);

        useCase.revise();

        verify(pendingRideRepository, times(3)).poll();
        verify(beginPendingRide).begin(pendingRide1);
        verify(beginPendingRide).begin(pendingRide2);
        verify(pendingRideRepository).addAll(List.of(pendingRideDTO1));
    }
}
