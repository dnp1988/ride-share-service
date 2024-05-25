package com.ride.share.integration;

import com.ride.share.api.CarRequest;
import com.ride.share.api.CarResponse;
import com.ride.share.api.RideRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.ride.share.api.ApiConstants.PATH_CARS;
import static com.ride.share.api.ApiConstants.PATH_END_RIDE;
import static com.ride.share.api.ApiConstants.PATH_RIDES;
import static com.ride.share.api.ApiConstants.PATH_FIND_RIDE;
import static com.ride.share.tools.TestConstants.CAR_ID_1;
import static com.ride.share.tools.TestConstants.CAR_ID_2;
import static com.ride.share.tools.TestConstants.CAR_SEATS_1;
import static com.ride.share.tools.TestConstants.CAR_SEATS_2;
import static com.ride.share.tools.TestConstants.RIDE_ID_1;
import static com.ride.share.tools.TestConstants.RIDE_PEOPLE_1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class RideShareApiIntegrationTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void itShouldManageRide(@Autowired MockMvc mvc) throws Exception {
        List<CarRequest> carRequests = List.of(
                new CarRequest(CAR_ID_1, CAR_SEATS_1),
                new CarRequest(CAR_ID_2, CAR_SEATS_2));
        RideRequest ride = new RideRequest(RIDE_ID_1, RIDE_PEOPLE_1);
        CarResponse carResponse = new CarResponse(CAR_ID_1, CAR_SEATS_1, CAR_SEATS_1 - RIDE_PEOPLE_1);

        mvc.perform(post(PATH_CARS).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequests)))
                .andExpect(status().isOk());

        mvc.perform(post(PATH_RIDES).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ride)))
                .andExpect(status().isAccepted());

        mvc.perform(post(PATH_FIND_RIDE.replace("{id}", String.valueOf(ride.getId()))))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(carResponse)));

        mvc.perform(post(PATH_END_RIDE.replace("{id}", String.valueOf(ride.getId()))))
                .andExpect(status().isNoContent());
    }
}
