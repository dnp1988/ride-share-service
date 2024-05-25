package com.ride.share.configuration;

import com.ride.share.api.CarRequest;
import com.ride.share.api.CarResponse;
import com.ride.share.configuration.mapper.CarMapper;
import com.ride.share.domain.entity.Car;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarMapperTest {

    private static final Long CAR_ID = 1L;
    private static final Integer CAR_SEATS = 4;
    private static final Integer CAR_AVAILABLE_SEATS = 0;

    private CarMapper carMapper = new CarMapper();

    @Test
    public void testMapperCarToCarResponse() {
        assertEquals(
                new CarResponse(CAR_ID, CAR_SEATS, CAR_AVAILABLE_SEATS),
                carMapper.map(new Car(CAR_ID, CAR_SEATS, CAR_AVAILABLE_SEATS))
        );
    }

    @Test
    public void testMapperCarRequestToCar() {
        assertEquals(
                new Car(CAR_ID, CAR_SEATS),
                carMapper.map(new CarRequest(CAR_ID, CAR_SEATS))
        );
    }

    @Test
    public void testMapperCarRequestListToCar() {
        assertEquals(
                List.of(new Car(CAR_ID, CAR_SEATS)),
                carMapper.map(List.of(new CarRequest(CAR_ID, CAR_SEATS)))
        );
    }
}
