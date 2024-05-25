package com.ride.share.configuration;

import com.ride.share.configuration.mapper.CarDTOMapper;
import com.ride.share.domain.dto.CarDTO;
import com.ride.share.domain.entity.Car;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarDTOMapperTest {

    private static final Long CAR_ID = 1L;
    private static final Integer CAR_SEATS = 4;
    private static final Integer CAR_AVAILABLE_SEATS = 0;

    private CarDTOMapper carDTOMapper = new CarDTOMapper();

    @Test
    public void testMapperCarDTOToCar() {
        assertEquals(
                new Car(CAR_ID, CAR_SEATS, CAR_AVAILABLE_SEATS),
                carDTOMapper.map(new CarDTO(CAR_ID, CAR_SEATS, CAR_AVAILABLE_SEATS))
        );
    }

    @Test
    public void testMapperCarsToCarDTOs() {
        assertEquals(
                List.of(new CarDTO(CAR_ID, CAR_SEATS)),
                carDTOMapper.map(List.of(new Car(CAR_ID, CAR_SEATS)))
        );
    }
}
