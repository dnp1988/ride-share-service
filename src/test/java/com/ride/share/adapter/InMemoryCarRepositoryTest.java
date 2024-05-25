package com.ride.share.adapter;

import com.ride.share.domain.dto.CarDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class InMemoryCarRepositoryTest {

    private static final Long CAR_ID_1 = 1L;
    private static final Long CAR_ID_2 = 2L;
    private static final Long CAR_ID_3 = 3L;
    private static final Long CAR_ID_4 = 4L;
    private static final Integer CAR_SEATS_1 = 4;
    private static final Integer CAR_SEATS_2 = 5;
    private static final Integer CAR_SEATS_3 = 6;
    private static final Integer CAR_SEATS_4 = 6;

    private InMemoryCarRepository repository;

    private CarDTO car1;
    private CarDTO car2;
    private CarDTO car3;
    private CarDTO car4;

    @BeforeEach
    public void beforeEach() {
        this.repository = new InMemoryCarRepository();
        this.car1 = new CarDTO(CAR_ID_1, CAR_SEATS_1);
        this.car2 = new CarDTO(CAR_ID_2, CAR_SEATS_2);
        this.car3 = new CarDTO(CAR_ID_3, CAR_SEATS_3);
        this.car4 = new CarDTO(CAR_ID_4, CAR_SEATS_4);
        repository.saveAll(List.of(car1, car2));
    }

    @Test
    public void testGetCar() {
        assertEquals(car1, repository.get(CAR_ID_1));
        assertEquals(car2, repository.get(CAR_ID_2));
        assertNull(repository.get(CAR_ID_3));
    }

    @Test
    public void testSaveCar() {
        repository.save(car3);
        assertEquals(car3, repository.get(CAR_ID_3));
    }

    @Test
    public void testSaveRideExistingId() {
        assertThrowsExactly(IllegalArgumentException.class, () -> repository.save(car1));
    }

    @Test
    public void testSaveCars() {
        repository.saveAll(List.of(car3, car4));
        assertEquals(car3, repository.get(CAR_ID_3));
        assertEquals(car4, repository.get(CAR_ID_4));
    }

    @Test
    public void testClear() {
        repository.clear();
        assertNull(repository.get(CAR_ID_1));
        assertNull(repository.get(CAR_ID_2));
    }

    @Test
    public void testTakeSeats() {
        Integer takeSeats = 2;

        CarDTO car = repository.takeSeats(takeSeats);
        assertEquals(CAR_ID_1, car.getId());
        assertEquals(CAR_SEATS_1, car.getMaxSeats());
        assertEquals(takeSeats, car.getAvailableSeats());
    }

    @Test
    public void testTakeSeatsNoCar() {
        Integer takeSeats = 6;

        CarDTO car = repository.takeSeats(takeSeats);
        assertNull(car);
        assertEquals(CAR_SEATS_1, repository.get(CAR_ID_1).getAvailableSeats());
        assertEquals(CAR_SEATS_2, repository.get(CAR_ID_2).getAvailableSeats());
    }

    @Test
    public void testReleaseSeats() {
        Integer takeSeats = 2;

        CarDTO takeSeatsCar = repository.takeSeats(takeSeats);
        assertEquals(takeSeats, takeSeatsCar.getAvailableSeats());

        CarDTO releaseSeatsCar = repository.releaseSeats(takeSeatsCar.getId(), takeSeats);
        assertEquals(CAR_ID_1, releaseSeatsCar.getId());
        assertEquals(CAR_SEATS_1, releaseSeatsCar.getMaxSeats());
        assertEquals(CAR_SEATS_1, releaseSeatsCar.getAvailableSeats());
    }
}
