package com.ride.share.domain.exception;

/**
 * Exception used in the case of Car ID collision.
 */
public class DuplicatedIdException extends IllegalArgumentException {

    /**
     * Creates a regular instance.
     *
     * @param message error message
     */
    public DuplicatedIdException(final String message) {
        super(message);
    }
}
