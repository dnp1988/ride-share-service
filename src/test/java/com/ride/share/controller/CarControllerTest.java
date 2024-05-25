package com.ride.share.controller;

import com.ride.share.api.CarRequest;
import com.ride.share.configuration.mapper.CarMapper;
import com.ride.share.domain.entity.Car;
import com.ride.share.domain.usecase.ResetCars;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.ride.share.api.ApiConstants.PATH_CARS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {CarController.class, CarMapper.class})
public class CarControllerTest {

    private static final Long CAR_ID_1 = 1L;
    private static final Long CAR_ID_2 = 2L;
    private static final Integer CAR_SEATS_1 = 4;
    private static final Integer CAR_SEATS_2 = 6;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResetCars resetCars;

    @Autowired
    private CarMapper carMapper;

    @Test
    public void testPutCarsOk() throws Exception {
        List<CarRequest> carRequestList = List.of(
                new CarRequest(CAR_ID_1, CAR_SEATS_1),
                new CarRequest(CAR_ID_2, CAR_SEATS_2));

        List<Car> carList = List.of(
                new Car(CAR_ID_1, CAR_SEATS_1),
                new Car(CAR_ID_2, CAR_SEATS_2));

        mockMvc.perform(post(PATH_CARS).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequestList)))
                .andExpect(status().isOk());

        Mockito.verify(resetCars).reset(carList);
    }

    @Test
    public void testPutCarsBadRequest() throws Exception {
        mockMvc.perform(post(PATH_CARS).contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(resetCars);
    }
}
