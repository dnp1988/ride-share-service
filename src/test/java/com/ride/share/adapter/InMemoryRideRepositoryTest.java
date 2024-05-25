package com.ride.share.adapter;

import com.ride.share.domain.dto.RideDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ride.share.tools.TestConstants.CAR_ID_1;
import static com.ride.share.tools.TestConstants.RIDE_ID_1;
import static com.ride.share.tools.TestConstants.RIDE_ID_2;
import static com.ride.share.tools.TestConstants.RIDE_ID_3;
import static com.ride.share.tools.TestConstants.RIDE_ID_4;
import static com.ride.share.tools.TestConstants.RIDE_PEOPLE_1;
import static com.ride.share.tools.TestConstants.RIDE_PEOPLE_2;
import static com.ride.share.tools.TestConstants.RIDE_PEOPLE_3;
import static com.ride.share.tools.TestConstants.RIDE_PEOPLE_4;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;


public class InMemoryRideRepositoryTest {

    private InMemoryRideRepository repository;

    private RideDTO ride1;
    private RideDTO ride2;
    private RideDTO ride3;
    private RideDTO ride4;

    @BeforeEach
    public void beforeEach() {
        this.repository = new InMemoryRideRepository();
        this.ride1 = new RideDTO(RIDE_ID_1, RIDE_PEOPLE_1);
        this.ride2 = new RideDTO(RIDE_ID_2, RIDE_PEOPLE_2);
        this.ride3 = new RideDTO(RIDE_ID_3, RIDE_PEOPLE_3);
        this.ride4 = new RideDTO(RIDE_ID_4, RIDE_PEOPLE_4);
        repository.saveAll(List.of(ride1, ride2));
    }

    @Test
    public void testGetRide() {
        assertEquals(ride1, repository.get(RIDE_ID_1));
        assertEquals(ride2, repository.get(RIDE_ID_2));
        assertNull(repository.get(RIDE_ID_3));
    }

    @Test
    public void testSaveRide() {
        repository.save(ride3);
        assertEquals(ride3, repository.get(RIDE_ID_3));
    }

    @Test
    public void testSaveRideExistingId() {
        assertThrowsExactly(IllegalArgumentException.class, () -> repository.save(ride1));
    }

    @Test
    public void testSaveRides() {
        repository.saveAll(List.of(ride3, ride4));
        assertEquals(ride3, repository.get(RIDE_ID_3));
        assertEquals(ride4, repository.get(RIDE_ID_4));
    }

    @Test
    public void testClear() {
        repository.clear();
        assertNull(repository.get(RIDE_ID_1));
        assertNull(repository.get(RIDE_ID_2));
    }

    @Test
    public void testRemoveRide() {
        repository.remove(RIDE_ID_1);
        assertNull(repository.get(RIDE_ID_1));
        assertNotNull(repository.get(RIDE_ID_2));
    }

    @Test
    public void testUpdateAssignment() {
        assertNull(repository.get(RIDE_ID_1).getAssignedToCarId());
        repository.updateAssignment(RIDE_ID_1, CAR_ID_1);
        assertEquals(CAR_ID_1, repository.get(RIDE_ID_1).getAssignedToCarId());
    }
}
