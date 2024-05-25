package com.ride.share.domain.usecase;

import com.ride.share.configuration.mapper.CarDTOMapper;
import com.ride.share.domain.entity.Car;
import com.ride.share.domain.exception.DuplicatedIdException;
import com.ride.share.domain.repository.CarRepository;
import com.google.common.base.Verify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;


/**
 * Clears all existing Rides, Pending Rides and Cars and creates a given list of Cars in their place.
 * If there is any issue in the creation of the new Cars, the old data is not recovered.
 */
@Component
public class ResetCars {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetCars.class);
    private static final Integer SEATS_MIN = 4;
    private static final Integer SEATS_MAX = 6;
    private static final Integer CAR_ID_MIN = 1;

    private ClearAllData clearAllData;
    private CarRepository carRepository;
    private CarDTOMapper carDTOMapper;

    /**
     * Creates a regular use case instance.
     *
     * @param clearAllData  Use Case of ClearAllData
     * @param carRepository Repository for Cars
     * @param carDTOMapper  Mapper of Car DTO
     */
    public ResetCars(ClearAllData clearAllData, CarRepository carRepository, CarDTOMapper carDTOMapper) {
        this.clearAllData = clearAllData;
        this.carRepository = carRepository;
        this.carDTOMapper = carDTOMapper;
    }

    /**
     * Clears all existing Rides, Pending Rides and Cars and creates a given list of Cars in their place.
     * If there is any issue in the creation of the new Cars, the old data is not recovered.
     * <p>
     * Car ID needs to be a positive number.
     * Car seats need to be between {@value #SEATS_MIN} and {@value #SEATS_MAX}.
     *
     * @param cars Cars to replace the existing ones
     * @throws DuplicatedIdException                  if there is a Car ID collision in the given Cars
     * @throws com.google.common.base.VerifyException if Cars input is not valid
     */
    public void reset(List<Car> cars) {
        clearAllData.clear();

        LOGGER.info("Cars to reset {}", cars);

        verifyCars(cars);

        carRepository.saveAll(carDTOMapper.map(cars));
    }

    private void verifyCars(List<Car> cars) {
        HashSet<Long> ids = new HashSet<>();

        Verify.verifyNotNull(cars, "Cars cannot be null");

        for (Car car : cars) {
            Verify.verifyNotNull(car.getId(),
                    "Car ID cannot be null");
            Verify.verify(car.getId() >= CAR_ID_MIN,
                    "Car(%s) ID must be %s or higher", car.getId(), CAR_ID_MIN);
            Verify.verify(car.getMaxSeats() >= SEATS_MIN && car.getMaxSeats() <= SEATS_MAX,
                    "Car(%s) seats must be between %s and %s", car.getId(), SEATS_MIN, SEATS_MAX);

            if (ids.contains(car.getId())) {
                String message = String.format("Car ID %s is duplicated", car.getId());
                throw new DuplicatedIdException(message);
            } else {
                ids.add(car.getId());
            }
        }
    }
}
