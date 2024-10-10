package com.drop.solution.parking.lot.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested parking lot is full in the parking system.
 * This exception results in a 409 CONFLICT HTTP response status.
 */
@ResponseStatus(code = HttpStatus.CONFLICT)
public class ParkingLotFullException extends RuntimeException {

    /**
     * Constructs a new  ParkingLotFullException with the specified detail message.
     *
     * @param message the detail message, saved for later retrieval by the
     *                {@link Throwable#getMessage()} method
     */
    public ParkingLotFullException(String message) {
        super(message);
        // Log the exception message at the warning level
        Logger logger = LoggerFactory.getLogger(ParkingLotFullException.class);
        logger.warn("ParkingLotFullException: {}", message);
    }
}
