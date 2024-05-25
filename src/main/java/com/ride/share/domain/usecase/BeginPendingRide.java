package com.ride.share.domain.usecase;

import com.ride.share.domain.dto.CarDTO;
import com.ride.share.domain.dto.RideDTO;
import com.ride.share.domain.entity.RideStatus;
import com.ride.share.domain.entity.PendingRide;
import com.ride.share.domain.repository.CarRepository;
import com.ride.share.domain.repository.RideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;


/**
 * Attempts to assign a Car to the given PendingRide.
 * Looks for a Car with enough available seats for the Ride passengers.
 * If a Car is found, attempts to update the Ride with the assigned Car.
 */
@Component
public class BeginPendingRide {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeginPendingRide.class);
    private static final String LOG_PREFIX = "PendingRide({})";

    private RideRepository rideRepository;
    private CarRepository carRepository;

    /**
     * Creates a regular use case instance.
     *
     * @param rideRepository Repository for Rides
     * @param carRepository     Repository for Cars
     */
    public BeginPendingRide(RideRepository rideRepository,
                            CarRepository carRepository) {
        this.rideRepository = rideRepository;
        this.carRepository = carRepository;
    }

    /**
     * Attempts to assign a Car to the given PendingRide.
     * Looks for a Car with enough available seats for the Ride passengers.
     * If a Car is found, attempts to update the Ride with the assigned Car.
     *
     * @param pendingRide Pending Ride to assign
     * @return Result of Ride assignment attempt
     */
    public RideStatus begin(PendingRide pendingRide) {
        Long rideId = pendingRide.getId();
        Integer passengers = pendingRide.getPassengers();

        //Attempt to lock seats for the Ride from any available Car
        CarDTO carDTO = carRepository.takeSeats(passengers);

        if (Objects.nonNull(carDTO)) {
            //A Car was found with enough available seats

            //Attempt to update Ride with the assigned Car
            RideDTO rideDTO = rideRepository.updateAssignment(rideId, carDTO.getId());

            if (Objects.nonNull(rideDTO)) {
                //Ride was updated with the assigned Car

                LOGGER.info(LOG_PREFIX + " was assigned to Car({})", rideId, carDTO.getId());
                return RideStatus.ASSIGNED;
            } else {
                //Ride was not found

                LOGGER.info(LOG_PREFIX + " was cancelled ({} seats released in Car({}))", rideId, passengers,
                        carDTO.getId());
                carRepository.releaseSeats(carDTO.getId(), passengers);
                return RideStatus.CANCELLED;
            }
        } else {
            //NO Car was found with enough seats

            LOGGER.info(LOG_PREFIX + " was NOT assigned to any Car (Ride still pending)", rideId);
            return RideStatus.UNASSIGNED;
        }
    }

}
