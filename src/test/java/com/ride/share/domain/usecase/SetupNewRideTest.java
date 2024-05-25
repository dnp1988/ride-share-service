package com.ride.share.domain.usecase;

import com.ride.share.domain.entity.Ride;
import org.junit.jupiter.api.Test;

import static com.ride.share.tools.TestConstants.RIDE_ID_1;
import static com.ride.share.tools.TestConstants.RIDE_PEOPLE_1;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SetupNewRideTest {

    private CreateRide createRide = mock(CreateRide.class);
    private QueueNewRide queueNewRide = mock(QueueNewRide.class);
    private RevisePendingRides revisePendingRides = mock(RevisePendingRides.class);

    private SetupNewRide useCase = new SetupNewRide(createRide, queueNewRide, revisePendingRides);

    @Test
    public void testSetupNewRideTest() {
        Ride ride = new Ride(RIDE_ID_1, RIDE_PEOPLE_1);
        useCase.setup(ride);
        verify(createRide).create(ride);
        verify(queueNewRide).queue(ride);
        verify(revisePendingRides).revise();
    }
}
