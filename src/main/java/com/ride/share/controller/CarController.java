package com.ride.share.controller;

import com.ride.share.api.ApiConstants;
import com.ride.share.api.CarRequest;
import com.ride.share.configuration.mapper.CarMapper;
import com.ride.share.domain.usecase.ResetCars;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ride.share.api.ApiConstants.PATH_CARS;

/**
 * Handles the Car related API endpoints.
 */
@RestController
@RequestMapping(PATH_CARS)
public final class CarController {

    private CarMapper carMapper;
    private ResetCars resetCars;

    /**
     * Creates a regular instance.
     *
     * @param resetCars Use case of ResetCars
     * @param carMapper Mapper for Car
     */
    public CarController(ResetCars resetCars,
                         CarMapper carMapper) {
        this.resetCars = resetCars;
        this.carMapper = carMapper;
    }

    /**
     * Exposes the <b>PUT {@value ApiConstants#PATH_CARS}</b> endpoint.
     * Removes all existing Rides, Pending Rides and Cars and creates the given Cars in their place.
     * If there is any issue in the creation of the new Cars, the old data is not recovered.
     *
     * @param cars Cars to create
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void resetCars(@RequestBody final List<CarRequest> cars) {
        resetCars.reset(carMapper.map(cars));
    }

}
