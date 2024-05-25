package com.ride.share.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.ride.share.api.ApiConstants.PATH_CHECK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheckController.class)
public class CheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCheckOk() throws Exception {
        mockMvc.perform(get(PATH_CHECK))
                .andExpect(status().isOk());
    }
}
