package com.ride.share.configuration.mapper;

import com.ride.share.domain.dto.CarDTO;
import com.ride.share.domain.entity.Car;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Maps values between {@link CarDTO} and other Car related models.
 */
@Component
public class CarDTOMapper {

    /**
     * Maps a list of {@link Car} to a list of {@link CarDTO}.
     *
     * @param cars list of Cars to map
     * @return mapped DTO Cars
     */
    public List<CarDTO> map(List<Car> cars) {
        if (Objects.isNull(cars)) {
            return null;
        } else {
            return cars.stream()
                    .map(car -> new CarDTO(car.getId(), car.getMaxSeats()))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Maps a {@link CarDTO} to a {@link Car}.
     *
     * @param carDTO DTO Car to map
     * @return mapped Car
     */
    public Car map(CarDTO carDTO) {
        if (Objects.isNull(carDTO)) {
            return null;
        } else {
            return new Car(carDTO.getId(), carDTO.getMaxSeats(), carDTO.getAvailableSeats());
        }
    }
}
