package com.drop.solution.parking.lot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * A class representing a successful response from the API.
 *
 */
@RequiredArgsConstructor
@Getter
public class SuccessResponse {
    
    /**
     * A message describing the result of the operation.
     * This message will provide information to the client about the success 
     * of the operation performed.
     */
    private final String message;

}