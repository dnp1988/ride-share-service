package com.ride.share.integration;

import com.ride.share.domain.entity.Car;
import com.ride.share.domain.entity.Ride;
import com.ride.share.domain.usecase.EndRide;
import com.ride.share.domain.usecase.ResetCars;
import com.ride.share.domain.usecase.RetrieveRide;
import com.ride.share.domain.usecase.SetupNewRide;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;

import static com.ride.share.tools.TestConstants.CAR_ID_1;
import static com.ride.share.tools.TestConstants.RIDE_ID_1;
import static com.ride.share.tools.TestConstants.RIDE_ID_2;
import static com.ride.share.tools.TestConstants.RIDE_ID_3;
import static com.ride.share.tools.TestConstants.RIDE_ID_4;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;


@SpringBootTest
@ActiveProfiles("dev")
public class CreateRideIntegrationTest {

    @Autowired
    private ResetCars resetCars;

    @Autowired
    private SetupNewRide setupNewRide;

    @Autowired
    private EndRide endRide;

    @Autowired
    private RetrieveRide retrieveRide;

    @Test
    public void testRideOrderAndSingleCarShare() {
        Car car1 = new Car(CAR_ID_1, 6);
        Ride ride1 = new Ride(RIDE_ID_1, 6);
        Ride ride2 = new Ride(RIDE_ID_2, 1);
        Ride ride3 = new Ride(RIDE_ID_3, 1);
        Ride ride4 = new Ride(RIDE_ID_4, 5);

        resetCars.reset(List.of(car1));

        setupNewRide.setup(ride1);
        setupNewRide.setup(ride2);
        setupNewRide.setup(ride3);

        Car locateRideCar1 = retrieveRide.retrieve(ride1.getId()).getAssignedTo();
        assertEquals(car1.getMaxSeats() - ride1.getPassengers(), locateRideCar1.getAvailableSeats());

        endRide.end(ride1.getId());
        assertThrowsExactly(NoSuchElementException.class, () -> retrieveRide.retrieve(ride1.getId()));

        setupNewRide.setup(ride4);

        Car locateRideCar2 = retrieveRide.retrieve(ride2.getId()).getAssignedTo();
        Car locateRideCar3 = retrieveRide.retrieve(ride3.getId()).getAssignedTo();
        Car locateRideCar4 = retrieveRide.retrieve(ride4.getId()).getAssignedTo();
        assertEquals(locateRideCar2, locateRideCar3);
        assertEquals(car1.getMaxSeats() - ride2.getPassengers() - ride3.getPassengers(),
                locateRideCar2.getAvailableSeats());
        assertNull(locateRideCar4);
    }

    @Test
    public void testRideOrderAndRideCancel() {
        Car car1 = new Car(CAR_ID_1, 6);
        Ride ride1 = new Ride(RIDE_ID_1, 6);
        Ride ride2 = new Ride(RIDE_ID_2, 1);
        Ride ride3 = new Ride(RIDE_ID_3, 5);
        Ride ride4 = new Ride(RIDE_ID_4, 1);

        resetCars.reset(List.of(car1));

        setupNewRide.setup(ride1);
        setupNewRide.setup(ride2);
        setupNewRide.setup(ride3);

        Car locateRideCar1 = retrieveRide.retrieve(ride1.getId()).getAssignedTo();
        assertEquals(car1.getMaxSeats() - ride1.getPassengers(), locateRideCar1.getAvailableSeats());

        endRide.end(ride2.getId()); // Ride 2 Cancelled
        assertThrowsExactly(NoSuchElementException.class, () -> retrieveRide.retrieve(ride2.getId()));
        endRide.end(ride1.getId()); // Ride 1 Completed
        assertThrowsExactly(NoSuchElementException.class, () -> retrieveRide.retrieve(ride1.getId()));

        setupNewRide.setup(ride4);

        Car locateRideCar3 = retrieveRide.retrieve(ride3.getId()).getAssignedTo();
        Car locateRideCar4 = retrieveRide.retrieve(ride4.getId()).getAssignedTo();
        assertEquals(locateRideCar3, locateRideCar4);
        assertEquals(car1.getMaxSeats() - ride3.getPassengers() - ride4.getPassengers(),
                locateRideCar3.getAvailableSeats());
    }
}
