package com.ride.share.domain.usecase;

import com.ride.share.configuration.mapper.RideDTOMapper;
import com.ride.share.domain.dto.RideDTO;
import com.ride.share.domain.entity.Car;
import com.ride.share.domain.entity.Ride;
import com.ride.share.domain.repository.RideRepository;
import com.google.common.base.Verify;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Objects;


/**
 * Fetches an existing Ride with a given ID from its Repository and returns it.
 * If the Ride has a Car assigned, it is also fetched and added to the Ride.
 */
@Component
public class RetrieveRide {

    private static final Integer RIDE_ID_MIN = 1;

    private RetrieveCar retrieveCar;
    private RideRepository rideRepository;
    private RideDTOMapper rideDTOMapper;

    /**
     * Creates a regular use case instance.
     *
     * @param retrieveCar       Use Case of RetrieveCar
     * @param rideRepository Repository for Rides
     * @param rideDTOMapper  Mapper of Ride DTO
     */
    public RetrieveRide(RetrieveCar retrieveCar,
                        RideRepository rideRepository,
                        RideDTOMapper rideDTOMapper) {
        this.retrieveCar = retrieveCar;
        this.rideRepository = rideRepository;
        this.rideDTOMapper = rideDTOMapper;
    }

    /**
     * Fetches an existing Ride with a given ID from its Repository and returns it.
     * If the Ride has a Car assigned, it is also fetched and added to the Ride.
     * Ride ID needs to be a positive number.
     *
     * @param id ID of the Ride to retrieve
     * @return Retrieved Ride
     * @throws NoSuchElementException                 if a Ride with the given ID cannot be found
     * @throws com.google.common.base.VerifyException if Ride ID input is not valid
     */
    public Ride retrieve(Long id) {
        verifyId(id);

        RideDTO rideDTO = rideRepository.get(id);

        if (Objects.isNull(rideDTO)) {
            String message = String.format("Ride(%s) was not found", id);
            throw new NoSuchElementException(message);
        }

        Car car = null;
        if (Objects.nonNull(rideDTO.getAssignedToCarId())) {
            car = retrieveCar.retrieve(rideDTO.getAssignedToCarId());
        }

        Ride ride = rideDTOMapper.map(rideDTO);
        ride.setAssignedTo(car);
        return ride;
    }

    private void verifyId(Long id) {
        Verify.verifyNotNull(id, "Ride ID cannot be null");
        Verify.verify(id >= RIDE_ID_MIN,
                "Ride(%s) ID must be %s or higher", id, RIDE_ID_MIN);
    }
}
