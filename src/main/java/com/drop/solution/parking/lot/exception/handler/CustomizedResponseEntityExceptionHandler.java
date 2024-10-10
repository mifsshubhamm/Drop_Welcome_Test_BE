package com.drop.solution.parking.lot.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.drop.solution.parking.lot.exception.AlreadyParkedException;
import com.drop.solution.parking.lot.exception.CarNotFoundException;
import com.drop.solution.parking.lot.exception.InvalidSlotNumberException;
import com.drop.solution.parking.lot.exception.ParkingLotFullException;
import com.drop.solution.parking.lot.model.ErrorDetails;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;

import java.io.IOException;
import java.time.LocalDateTime;


/**
 * Global exception handler that customizes the response for various exceptions 
 * that may occur in the application.
 *
 * This class extends ResponseEntityExceptionHandler to provide centralized 
 * exception handling and formatting for responses. It logs the exceptions and 
 * constructs a standardized error response containing relevant details.
 */
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomizedResponseEntityExceptionHandler.class);

    /**
     * Handles all unhandled exceptions in the application.
     *
     * @param ex the exception that was thrown.
     * @param request the current web request.
     * @return a ResponseEntity containing an ErrorDetails object 
     *         with error information and a 500 Internal Server Error status.
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error("An error occurred: {}", ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles IO exceptions that occur in the application.
     *
     * @param ex the IOException that was thrown.
     * @param request the current web request.
     * @return a ResponseEntity containing an ErrorDetails object 
     *         with error information and a 500 Internal Server Error status.
     */
    @ExceptionHandler(IOException.class)
    public final ResponseEntity<ErrorDetails> handleIOException(Exception ex, WebRequest request) {
        logger.error("IO error occurred: {}", ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles exceptions related to invalid credentials.
     *
     * @param ex the BadCredentialsException that was thrown.
     * @param request the current web request.
     * @return a ResponseEntity containing an ErrorDetails object 
     *         with an appropriate error message and a 400 Bad Request status.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<ErrorDetails> handleBadCredentialsException(Exception ex, WebRequest request) {
        logger.warn("Invalid credentials attempt: {}", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Invalid credentials",
                HttpStatus.BAD_REQUEST.value(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions thrown when a requested car is not found.
     *
     * @param ex the CarNotFoundException that was thrown.
     * @param request the current web request.
     * @return a ResponseEntity containing an ErrorDetails object 
     *         with an error message and a 404 Not Found status.
     */
    @ExceptionHandler(CarNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleCarNotFoundException(Exception ex, WebRequest request) {
        logger.warn("Car not found: {}", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                HttpStatus.NOT_FOUND.value(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions when a car is already parked.
     *
     * @param ex the AlreadyParkedException that was thrown.
     * @param request the current web request.
     * @return a ResponseEntity containing an ErrorDetails object 
     *         with an appropriate error message and a 400 Bad Request status.
     */
    @ExceptionHandler(AlreadyParkedException.class)
    public final ResponseEntity<ErrorDetails> handleAlreadyParkedException(Exception ex, WebRequest request) {
        logger.warn("Already Parked: {}", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions when the parking lot is full.
     *
     * @param ex the ParkingLotFullException that was thrown.
     * @param request the current web request.
     * @return a ResponseEntity containing an ErrorDetails object 
     *         with an appropriate error message and a 409 Conflict status.
     */
    @ExceptionHandler(ParkingLotFullException.class)
    public final ResponseEntity<ErrorDetails> handleParkingLotFullException(Exception ex, WebRequest request) {
        logger.warn("Parking Lot is Full: {}", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                HttpStatus.CONFLICT.value(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }
    
    /**
     * Handles exceptions when the parking lot is full.
     *
     * @param ex the ParkingLotFullException that was thrown.
     * @param request the current web request.
     * @return a ResponseEntity containing an ErrorDetails object 
     *         with an appropriate error message and a 409 Conflict status.
     */
    @ExceptionHandler(InvalidSlotNumberException.class)
    public final ResponseEntity<ErrorDetails> handleInvalidSlotNumberException(Exception ex, WebRequest request) {
        logger.warn("Invalid Slot Number Exception: {}", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles validation errors from method arguments.
     *
     * @param ex the MethodArgumentNotValidException that was thrown.
     * @param headers the headers for the response.
     * @param status the status of the response.
     * @param request the current web request.
     * @return a ResponseEntity containing an ErrorDetails object 
     *         with validation error details and a 400 Bad Request status.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
                                                                   HttpHeaders headers, 
                                                                   HttpStatusCode status, 
                                                                   WebRequest request) {
        BindingResult result = ex.getBindingResult();
        StringBuilder errors = new StringBuilder();
        
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> {
                String message = error.getDefaultMessage();
                errors.append(message).append("\n");
            });
            logger.warn("Validation errors occurred: {}", errors);
            ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), errors.toString(), 
                    HttpStatus.BAD_REQUEST.value(), request.getDescription(false));
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        } else {
            String error = "Total Errors: " + ex.getErrorCount() + 
                            " First Error: " + ex.getFieldError().getDefaultMessage();
            logger.warn("Validation errors occurred: {}", errors.toString());
            ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), error, 
                    HttpStatus.BAD_REQUEST.value(), request.getDescription(false));
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Handles exceptions related to rate limiting.
     *
     * @return a ResponseEntity containing an ErrorDetails object 
     *         indicating too many requests and a 429 Too Many Requests status.
     */
    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<ErrorDetails> handleRateLimitException() {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                "Too many requests. Please try again later.", 
                HttpStatus.TOO_MANY_REQUESTS.value(), "");
        return new ResponseEntity<>(errorDetails, HttpStatus.TOO_MANY_REQUESTS);
    }
}

