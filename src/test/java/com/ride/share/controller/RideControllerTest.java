package com.ride.share.controller;

import com.ride.share.api.CarResponse;
import com.ride.share.api.RideRequest;
import com.ride.share.configuration.mapper.CarMapper;
import com.ride.share.configuration.mapper.RideMapper;
import com.ride.share.domain.entity.Car;
import com.ride.share.domain.entity.Ride;
import com.ride.share.domain.usecase.EndRide;
import com.ride.share.domain.usecase.RetrieveRide;
import com.ride.share.domain.usecase.SetupNewRide;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.ride.share.api.ApiConstants.PATH_END_RIDE;
import static com.ride.share.api.ApiConstants.PATH_RIDES;
import static com.ride.share.api.ApiConstants.PATH_FIND_RIDE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({RideController.class, RideMapper.class, CarMapper.class})
public class RideControllerTest {

    private static final long RIDE_ID = 1L;
    private static final long CAR_ID = 1L;
    private static final Integer SEATS = 4;
    private static final Integer PEOPLE = 4;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private RideMapper rideMapper;

    @MockBean
    private SetupNewRide setupNewRide;

    @MockBean
    private RetrieveRide retrieveRide;

    @MockBean
    private EndRide endRide;

    @Test
    public void testCreateRideOk() throws Exception {
        RideRequest rideRequest = new RideRequest(RIDE_ID, PEOPLE);
        Ride ride = new Ride(RIDE_ID, PEOPLE);

        mockMvc.perform(post(PATH_RIDES).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rideRequest)))
                .andExpect(status().isAccepted());

        Mockito.verify(setupNewRide).setup(ride);
    }

    @Test
    public void testFindRideOk() throws Exception {
        Car car = new Car(CAR_ID, SEATS, 0);
        Ride ride = new Ride(RIDE_ID, PEOPLE, car);
        CarResponse carResponse = new CarResponse(CAR_ID, SEATS, 0);

        Mockito.when(retrieveRide.retrieve(RIDE_ID)).thenReturn(ride);

        mockMvc.perform(post(PATH_FIND_RIDE.replace("{id}", String.valueOf(RIDE_ID))))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(carResponse)));

        Mockito.verify(retrieveRide).retrieve(RIDE_ID);
    }

    @Test
    public void testEndRideOk() throws Exception {
        mockMvc.perform(post(PATH_END_RIDE.replace("{id}", String.valueOf(RIDE_ID))))
                .andExpect(status().isNoContent());

        Mockito.verify(endRide).end(RIDE_ID);
    }
}
