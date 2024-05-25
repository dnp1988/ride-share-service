package com.ride.share.domain.usecase;

import com.ride.share.configuration.mapper.RideDTOMapper;
import com.ride.share.domain.entity.Ride;
import com.ride.share.domain.repository.RideRepository;
import com.google.common.base.Verify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Creates a new Ride and saves it in its Repository.
 */
@Component
public class CreateRide {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateRide.class);
    private static final Integer PASSENGERS_MIN = 1;
    private static final Integer PASSENGERS_MAX = 6;
    private static final Integer RIDE_ID_MIN = 1;

    private RideRepository rideRepository;
    private RideDTOMapper rideDTOMapper;

    /**
     * Creates a regular use case instance.
     *
     * @param rideRepository Repository for Rides
     * @param rideDTOMapper  Mapper of Ride DTO
     */
    public CreateRide(RideRepository rideRepository, RideDTOMapper rideDTOMapper) {
        this.rideRepository = rideRepository;
        this.rideDTOMapper = rideDTOMapper;
    }

    /**
     * Creates a new Ride with the given data and saves it in its Repository.
     * <p>
     * Ride ID needs to be a positive number.
     * Ride passengers need to be between {@value #PASSENGERS_MIN} and {@value #PASSENGERS_MAX}.
     *
     * @param ride New Ride to create
     * @throws com.google.common.base.VerifyException if Ride input is not valid
     */
    public void create(Ride ride) {
        verifyRide(ride);

        LOGGER.info("Ride to create {}", ride);

        rideRepository.save(rideDTOMapper.map(ride));
    }

    private void verifyRide(Ride ride) {
        Verify.verifyNotNull(ride,
                "Ride cannot be null");
        Verify.verifyNotNull(ride.getId(),
                "Ride ID cannot be null");
        Verify.verify(ride.getId() >= RIDE_ID_MIN,
                "Ride(%s) ID must be %s or higher", ride.getId(), RIDE_ID_MIN);
        Verify.verify(ride.getPassengers() >= PASSENGERS_MIN && ride.getPassengers() <= PASSENGERS_MAX,
                "Ride(%s) passengers must be between %s and %s", ride.getId(), PASSENGERS_MIN, PASSENGERS_MAX);
    }
}
