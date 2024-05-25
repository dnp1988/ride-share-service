package com.ride.share.controller;

import com.ride.share.api.ApiConstants;
import com.ride.share.api.CarResponse;
import com.ride.share.api.RideRequest;
import com.ride.share.configuration.mapper.CarMapper;
import com.ride.share.configuration.mapper.RideMapper;
import com.ride.share.domain.entity.Car;
import com.ride.share.domain.usecase.EndRide;
import com.ride.share.domain.usecase.RetrieveRide;
import com.ride.share.domain.usecase.SetupNewRide;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static com.ride.share.api.ApiConstants.PARAM_ID;
import static com.ride.share.api.ApiConstants.PATH_END_RIDE;
import static com.ride.share.api.ApiConstants.PATH_RIDES;
import static com.ride.share.api.ApiConstants.PATH_FIND_RIDE;


/**
 * Handles the Ride related API endpoints.
 */
@RestController
public final class RideController {

    private RideMapper rideMapper;
    private CarMapper carMapper;
    private SetupNewRide setupNewRide;
    private EndRide endRide;
    private RetrieveRide retrieveRide;

    /**
     * Creates a regular instance.
     *
     * @param setupNewRide Use case of SetupNewRide
     * @param endRide      Use case of EndRide
     * @param retrieveRide Use case of RetrieveRide
     * @param rideMapper   Mapper for Ride
     * @param carMapper    Mapper for Car
     */
    public RideController(SetupNewRide setupNewRide,
                          EndRide endRide,
                          RetrieveRide retrieveRide,
                          RideMapper rideMapper,
                          CarMapper carMapper) {
        this.setupNewRide = setupNewRide;
        this.endRide = endRide;
        this.retrieveRide = retrieveRide;
        this.rideMapper = rideMapper;
        this.carMapper = carMapper;
    }

    /**
     * Exposes the <b>POST {@value ApiConstants#PATH_RIDES}</b> endpoint.
     * Creates a new Ride and sets it up to be assigned to a Car.
     *
     * @param ride Ride to create
     * @return empty body
     */
    @PostMapping(PATH_RIDES)
    public ResponseEntity<Void> createRide(@RequestBody final RideRequest ride) {
        setupNewRide.setup(rideMapper.map(ride));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * Exposes the <b>POST {@value ApiConstants#PATH_END_RIDE}</b> endpoint.
     * Ends an existing Ride with the given ID and releases any Car assigned to it.
     *
     * @param rideId ID of the Ride to end
     * @return empty body
     */
    @PostMapping(PATH_END_RIDE)
    public ResponseEntity<Void> endRide(@PathVariable(PARAM_ID) final long rideId) {
        endRide.end(rideId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Exposes the <b>POST {@value ApiConstants#PATH_FIND_RIDE}</b> endpoint.
     * Finds an existing Ride with the given ID and returns the Car assigned to it.
     *
     * @param rideId ID of the Ride to find
     * @return Car assigned to the Ride or empty body if no Car is assigned
     */
    @PostMapping(value = PATH_FIND_RIDE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<CarResponse> findRide(@PathVariable(PARAM_ID) final long rideId) {
        Car car = retrieveRide.retrieve(rideId).getAssignedTo();
        if (car == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(carMapper.map(car), HttpStatus.OK);
    }
}
