package com.ride.share.domain.usecase;

import com.ride.share.domain.entity.Car;
import com.ride.share.domain.entity.Ride;
import com.ride.share.domain.repository.CarRepository;
import com.ride.share.domain.repository.RideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;


/**
 * Ends an existing Ride with a given ID and releases any Car assigned to it.
 */
@Component
public class EndRide {

    private static final Logger LOGGER = LoggerFactory.getLogger(EndRide.class);

    private RetrieveRide retrieveRide;
    private RideRepository rideRepository;
    private CarRepository carRepository;

    /**
     * Creates a regular use case instance.
     *
     * @param retrieveRide   Use Case of RetrieveRide
     * @param rideRepository Repository for Rides
     * @param carRepository     Repository for Cars
     */
    public EndRide(RetrieveRide retrieveRide,
                   RideRepository rideRepository,
                   CarRepository carRepository) {
        this.retrieveRide = retrieveRide;
        this.rideRepository = rideRepository;
        this.carRepository = carRepository;
    }

    /**
     * Ends an existing Ride with a given ID and releases any Car assigned to it.
     *
     * @param id ID of Ride to end
     */
    public void end(Long id) {
        LOGGER.info("Ending Ride({})", id);

        Ride ride = retrieveRide.retrieve(id);

        if (Objects.nonNull(ride.getAssignedTo())) {
            Car assignedCar = ride.getAssignedTo();

            LOGGER.info("Releasing {} Seats in Car({})", ride.getPassengers(), assignedCar.getId());

            carRepository.releaseSeats(assignedCar.getId(), ride.getPassengers());
        }

        rideRepository.remove(id);
    }

}
