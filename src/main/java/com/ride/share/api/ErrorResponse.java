package com.ride.share.api;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * API model for the body of error outgoing responses.
 */
@Data
@AllArgsConstructor
public class ErrorResponse {

    /**
     * Outgoing error message.
     */
    private String message;
}
