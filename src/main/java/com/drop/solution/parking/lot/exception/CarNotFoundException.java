package com.drop.solution.parking.lot.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested car is not found in the parking system.
 * This exception results in a 404 Not Found HTTP response status.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CarNotFoundException extends RuntimeException {

    /**
     * Constructs a new CarNotFoundException with the specified detail message.
     *
     * @param message the detail message, saved for later retrieval by the
     *                {@link Throwable#getMessage()} method
     */
    public CarNotFoundException(String message) {
        super(message);
        // Log the exception message at the warning level
        Logger logger = LoggerFactory.getLogger(CarNotFoundException.class);
        logger.warn("CarNotFoundException: {}", message);
    }
}