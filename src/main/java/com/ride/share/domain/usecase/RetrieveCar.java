package com.ride.share.domain.usecase;

import com.ride.share.configuration.mapper.CarDTOMapper;
import com.ride.share.domain.dto.CarDTO;
import com.ride.share.domain.entity.Car;
import com.ride.share.domain.repository.CarRepository;
import com.google.common.base.Verify;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Objects;


/**
 * Fetches an existing Car with a given ID from its Repository and returns it.
 */
@Component
public class RetrieveCar {

    private static final Integer CAR_ID_MIN = 1;

    private CarRepository carRepository;
    private CarDTOMapper carDTOMapper;

    /**
     * Creates a regular use case instance.
     *
     * @param carRepository Repository for Cars
     * @param carDTOMapper  Mapper of Car DTO
     */
    public RetrieveCar(CarRepository carRepository, CarDTOMapper carDTOMapper) {
        this.carRepository = carRepository;
        this.carDTOMapper = carDTOMapper;
    }

    /**
     * Fetches an existing Car with a given ID from its Repository and returns it.
     * Car ID needs to be a positive number.
     *
     * @param id ID of Car to retrieve
     * @return Retrieved Car
     * @throws NoSuchElementException                 if a Car with the given ID cannot be found
     * @throws com.google.common.base.VerifyException if Car ID input is not valid
     */
    public Car retrieve(Long id) {
        verifyId(id);

        CarDTO carDTO = carRepository.get(id);

        if (Objects.isNull(carDTO)) {
            String message = String.format("Car(%s) was not found", id);
            throw new NoSuchElementException(message);
        }

        return carDTOMapper.map(carDTO);
    }

    private void verifyId(Long id) {
        Verify.verifyNotNull(id, "Car ID cannot be null");
        Verify.verify(id >= CAR_ID_MIN,
                "Car(%s) ID must be %s or higher", id, CAR_ID_MIN);
    }
}
