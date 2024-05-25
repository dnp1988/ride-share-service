package com.ride.share.domain.usecase;

import com.ride.share.configuration.mapper.CarDTOMapper;
import com.ride.share.domain.dto.CarDTO;
import com.ride.share.domain.entity.Car;
import com.ride.share.domain.exception.DuplicatedIdException;
import com.ride.share.domain.repository.CarRepository;
import com.google.common.base.VerifyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.ride.share.tools.TestConstants.CAR_ID_1;
import static com.ride.share.tools.TestConstants.CAR_ID_2;
import static com.ride.share.tools.TestConstants.CAR_SEATS_1;
import static com.ride.share.tools.TestConstants.CAR_SEATS_2;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

public class ResetCarsTest {

    private CarRepository carRepository = mock(CarRepository.class);
    private ClearAllData clearAllData = mock(ClearAllData.class);

    private ResetCars useCase = new ResetCars(clearAllData, carRepository, new CarDTOMapper());

    @Test
    public void testResetCarsTest() {
        List<Car> cars = List.of(new Car(CAR_ID_1, CAR_SEATS_1), new Car(CAR_ID_2, CAR_SEATS_2));
        Collection<CarDTO> carDTOs = List.of(new CarDTO(CAR_ID_1, CAR_SEATS_1), new CarDTO(CAR_ID_2, CAR_SEATS_2));

        useCase.reset(cars);

        verify(clearAllData).clear();
        verify(carRepository).saveAll(carDTOs);
    }

    @Test
    public void testResetCarsDuplicatedIdError() {
        List<Car> cars = List.of(new Car(CAR_ID_1, CAR_SEATS_1), new Car(CAR_ID_1, CAR_SEATS_1));
        assertThrowsExactly(DuplicatedIdException.class, () -> useCase.reset(cars));
        verify(clearAllData).clear();
        verifyNoInteractions(carRepository);
    }

    @ParameterizedTest
    @MethodSource("testResetCarsVerifyErrorParams")
    public void testResetCarsVerifyError(List<Car> cars) {
        assertThrowsExactly(VerifyException.class, () -> useCase.reset(cars));
        verify(clearAllData).clear();
        verifyNoInteractions(carRepository);
    }

    private static Stream<List<Car>> testResetCarsVerifyErrorParams() {
        return Stream.of(
                null,
                List.of(new Car(null, CAR_SEATS_1)),
                List.of(new Car(0L, CAR_SEATS_1)),
                List.of(new Car(CAR_ID_1, 3)),
                List.of(new Car(CAR_ID_1, 7))
        );
    }
}
