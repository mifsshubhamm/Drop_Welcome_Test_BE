package com.drop.solution.parking.lot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.drop.solution.parking.lot.model.CarDetailsRequest;
import com.drop.solution.parking.lot.model.SuccessResponse;
import com.drop.solution.parking.lot.service.ParkingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


/**
 * Controller for managing parking operations.
 * This REST controller provides endpoints to park, unpark, and retrieve information 
 * about parking slots.
 * 
 * It handles HTTP requests for parking cars, retrieving slot information, and unparking cars.
 * It uses the ParkingService to interact with the parking logic.
 * 
 */
@RestController
@RequestMapping("/api/parking")
@RequiredArgsConstructor
public class ParkingController {

    private static final Logger logger = LoggerFactory.getLogger(ParkingController.class);
    private final ParkingService parkingService;

    /**
     * Parks a car in the parking facility.
     *
     * @param newCar the details of the car to be parked, including the license plate.
     * @return a ResponseEntity containing a SuccessResponse with parking result.
     */
    @PostMapping("/park")
    public ResponseEntity<SuccessResponse> parkCar(@Valid @RequestBody CarDetailsRequest newCar)  {
        logger.info("Attempting to park car with license plate: %s", newCar.getLicensePlate());
        SuccessResponse response = parkingService.parkCar(newCar.getLicensePlate());
        logger.info("Parking response: {}", response);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves information about a specific parking slot.
     *
     * @param slot the slot number for which information is requested.
     * @return a ResponseEntity containing a SuccessResponse with slot information.
     */
    @GetMapping("/slot")
    public ResponseEntity<SuccessResponse> getSlot(@RequestParam int slot) {
        logger.info("Retrieving information for slot: %d", slot);
        SuccessResponse response = parkingService.getSlotInfo(slot);
        logger.info("Slot information: {}", response);
        return ResponseEntity.ok(response);
    }

    /**
     * Unparks a car from the parking facility.
     *
     * @param licensePlate the license plate of the car to be unparked.
     * @return a ResponseEntity containing a SuccessResponse with unparking result.
     */
    @DeleteMapping("/unpark")
    public ResponseEntity<SuccessResponse> unparkCar(@RequestParam String licensePlate) {
        logger.info("Attempting to unpark car with license plate: %s", licensePlate);
        SuccessResponse response = parkingService.unparkCar(licensePlate);
        logger.info("Unparking response: {}", response);
        return ResponseEntity.ok(response);
    }
}