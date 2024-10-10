package com.drop.solution.parking.lot.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * A data transfer object representing the details of a car 
 * that is to be parked in the parking system.
 *
 * This class contains the necessary information about the car,
 * specifically the license plate, which is required for parking operations.
 */
@Data
public class CarDetailsRequest {

    /**
     * The license plate of the car.
     * This field is required and must not be blank.
     *
     * @notblank message = "License plate is required."
     */
    @NotBlank(message = "License plate is required.")
    private String licensePlate; // The license plate of the car

}
