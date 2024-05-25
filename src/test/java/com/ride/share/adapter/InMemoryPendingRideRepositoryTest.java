package com.ride.share.adapter;

import com.ride.share.domain.dto.PendingRideDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ride.share.tools.TestConstants.RIDE_ID_1;
import static com.ride.share.tools.TestConstants.RIDE_ID_2;
import static com.ride.share.tools.TestConstants.RIDE_ID_3;
import static com.ride.share.tools.TestConstants.RIDE_PEOPLE_1;
import static com.ride.share.tools.TestConstants.RIDE_PEOPLE_2;
import static com.ride.share.tools.TestConstants.RIDE_PEOPLE_3;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class InMemoryPendingRideRepositoryTest {

    private static final int INITIAL_CAPACITY = 10;

    private InMemoryPendingRideRepository repository;

    private PendingRideDTO pendingRide1;
    private PendingRideDTO pendingRide2;
    private PendingRideDTO pendingRide3;

    @BeforeEach
    public void beforeEach() {
        this.repository = new InMemoryPendingRideRepository(INITIAL_CAPACITY);
        this.pendingRide1 = new PendingRideDTO(RIDE_ID_1, RIDE_PEOPLE_1);
        this.pendingRide2 = new PendingRideDTO(RIDE_ID_2, RIDE_PEOPLE_2);
        this.pendingRide3 = new PendingRideDTO(RIDE_ID_3, RIDE_PEOPLE_3);
        repository.addAll(List.of(pendingRide1));
    }

    @Test
    public void testPollRide() {
        assertEquals(pendingRide1, repository.poll());
        assertNull(repository.poll());
    }

    @Test
    public void testAddRide() {
        repository.add(pendingRide2);
        assertEquals(pendingRide1, repository.poll());
        assertEquals(pendingRide2, repository.poll());
        assertNull(repository.poll());
    }

    @Test
    public void testAddAllRides() {
        repository.addAll(List.of(pendingRide2, pendingRide3));
        assertEquals(pendingRide1, repository.poll());
        assertEquals(pendingRide2, repository.poll());
        assertEquals(pendingRide3, repository.poll());
        assertNull(repository.poll());
    }

    @Test
    public void testClear() {
        repository.clear();
        assertNull(repository.poll());
    }
}
