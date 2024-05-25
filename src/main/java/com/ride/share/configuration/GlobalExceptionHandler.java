package com.ride.share.configuration;

import com.ride.share.api.ErrorResponse;
import com.ride.share.domain.exception.DuplicatedIdException;
import com.google.common.base.VerifyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

/**
 * Configuration for error handling at a Controller level.
 * It defines the kind of HTTP responses that the API returns in case of a known error.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Creates the HTTP response for known cases of Bad Request related errors.
     * Returns a 400 response with a body containing an error message.
     *
     * @param ex Bad Request exception to handle
     * @return HTTP Bad Request response
     */
    @ExceptionHandler(value = {DuplicatedIdException.class, VerifyException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Creates the HTTP response for known cases of Not Found related errors.
     * Returns a 404 response with an empty body.
     *
     * @param ex Not Found exception to handle
     * @return HTTP Not Found response
     */
    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<Void> handle(NoSuchElementException ex) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
