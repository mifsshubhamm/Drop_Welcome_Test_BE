package com.drop.solution.parking.lot.model;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents error details that are returned in response to client requests
 * when exceptions occur. This class encapsulates the timestamp, message,
 * status code, and additional details related to the error.
 */
@Getter
@AllArgsConstructor
public class ErrorDetails {

    private LocalDateTime timeStamp; // The timestamp when the error occurred
    private String message;           // A descriptive message about the error
    private int statusCode;           // The HTTP status code associated with the error
    private String details;           // Additional details regarding the error

    /**
     * Returns a string representation of the ErrorDetails object.
     *
     * @return a string containing the error details formatted for logging
     */
    @Override
    public String toString() {
        return String.format("ErrorDetails{timeStamp=%s, message='%s', statusCode=%d, details='%s'}",
                timeStamp, message, statusCode, details);
    }
}
