package com.drop.solution.parking.lot.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



/**
 * Exception thrown when a requested car is already parked in the parking system.
 * This exception results in a 400 BAD REQUEST HTTP response status.
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AlreadyParkedException extends RuntimeException {

    /**
     * Constructs a new AlreadyParkedException with the specified detail message.
     *
     * @param message the detail message, saved for later retrieval by the
     *                {@link Throwable#getMessage()} method
     */
    public AlreadyParkedException(String message) {
        super(message);
        Logger logger = LoggerFactory.getLogger(AlreadyParkedException.class);
        logger.warn("AlreadyParkedException: {}", message);
    }
}