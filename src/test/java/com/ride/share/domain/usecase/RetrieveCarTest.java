package com.ride.share.domain.usecase;

import com.ride.share.configuration.mapper.CarDTOMapper;
import com.ride.share.domain.dto.CarDTO;
import com.ride.share.domain.entity.Car;
import com.ride.share.domain.repository.CarRepository;
import com.google.common.base.VerifyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static com.ride.share.tools.TestConstants.CAR_ID_1;
import static com.ride.share.tools.TestConstants.CAR_SEATS_1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class RetrieveCarTest {

    private CarRepository carRepository = mock(CarRepository.class);

    private RetrieveCar useCase = new RetrieveCar(carRepository, new CarDTOMapper());

    @Test
    public void testRetrieveCarTest() {
        CarDTO carDTO = new CarDTO(CAR_ID_1, CAR_SEATS_1);
        Car carEntity = new Car(CAR_ID_1, CAR_SEATS_1);

        when(carRepository.get(CAR_ID_1)).thenReturn(carDTO);

        Car result = useCase.retrieve(CAR_ID_1);
        assertEquals(carEntity, result);

        verify(carRepository).get(CAR_ID_1);
    }

    @Test
    public void testRetrieveCarNotFoundTest() {
        assertThrowsExactly(NoSuchElementException.class, () -> useCase.retrieve(CAR_ID_1));
        verify(carRepository).get(CAR_ID_1);
    }

    @ParameterizedTest
    @MethodSource("testRetrieveCarVerifyErrorParams")
    public void testRetrieveCarVerifyError(Long carId) {
        assertThrowsExactly(VerifyException.class, () -> useCase.retrieve(carId));
        verifyNoInteractions(carRepository);
    }

    private static Stream<Long> testRetrieveCarVerifyErrorParams() {
        return Stream.of(0L, -1L, null);
    }
}
