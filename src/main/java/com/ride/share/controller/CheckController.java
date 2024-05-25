package com.ride.share.controller;

import com.ride.share.api.ApiConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.ride.share.api.ApiConstants.PATH_CHECK;

/**
 * Handles the Server Status related API endpoints.
 */
@RestController
@RequestMapping(PATH_CHECK)
public final class CheckController {

    /**
     * Exposes the <b>GET {@value ApiConstants#PATH_CHECK}</b> endpoint.
     * Returns a response without a body.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void check() {
    }

}
