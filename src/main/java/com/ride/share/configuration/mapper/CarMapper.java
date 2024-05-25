package com.ride.share.configuration.mapper;

import com.ride.share.api.CarRequest;
import com.ride.share.api.CarResponse;
import com.ride.share.domain.entity.Car;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Maps values between {@link Car} and other Car API related models.
 */
@Component
public class CarMapper {

    /**
     * Maps a list of {@link CarRequest} to a list of {@link Car}.
     *
     * @param carRequests list of Car Requests to map
     * @return mapped Cars
     */
    public List<Car> map(List<CarRequest> carRequests) {
        if (Objects.isNull(carRequests)) {
            return null;
        } else {
            return carRequests.stream()
                    .map(this::map)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Maps a {@link CarRequest} to a {@link Car}.
     *
     * @param carRequest Car Request to map
     * @return mapped Car
     */
    public Car map(CarRequest carRequest) {
        if (Objects.isNull(carRequest)) {
            return null;
        } else {
            return new Car(carRequest.getId(), carRequest.getSeats());
        }
    }

    /**
     * Maps a {@link Car} to a {@link CarResponse}.
     *
     * @param car Car to map
     * @return mapped Car Response
     */
    public CarResponse map(Car car) {
        if (Objects.isNull(car)) {
            return null;
        } else {
            return new CarResponse(car.getId(), car.getMaxSeats(), car.getAvailableSeats());
        }
    }
}
