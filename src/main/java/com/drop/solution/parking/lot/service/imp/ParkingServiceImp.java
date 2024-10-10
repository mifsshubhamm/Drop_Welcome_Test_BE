package com.drop.solution.parking.lot.service.imp;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.drop.solution.parking.lot.exception.AlreadyParkedException;
import com.drop.solution.parking.lot.exception.CarNotFoundException;
import com.drop.solution.parking.lot.exception.InvalidSlotNumberException;
import com.drop.solution.parking.lot.exception.ParkingLotFullException;
import com.drop.solution.parking.lot.model.Car;
import com.drop.solution.parking.lot.model.SuccessResponse;
import com.drop.solution.parking.lot.service.ParkingService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

/**
 * Service class for managing parking operations.
 * This class handles the logic for parking and unparking cars,
 * as well as retrieving information about parking slots.
 */
@Service
public class ParkingServiceImp implements ParkingService {
    private static final Logger logger = LoggerFactory.getLogger(ParkingServiceImp.class);
    private final Map<Integer, Car> slots = new HashMap<>(); // Stores parked cars by slot number
    private final int size;

    /**
     * Constructs a ParkingServiceImp with the specified parking lot size.
     *
     * @param size the number of parking slots
     */
    public ParkingServiceImp(@Value("${parking.lot.size}") int size) {
        this.size = size;
        logger.info("ParkingServiceImp initialized with {} slots.", size);
    }

	

    /**
     * Parks a car with the given license plate in the parking lot.
     *
     * @param licensePlate the license plate of the car to park
     * @return a message indicating the result of the parking attempt
     */
    
    @RateLimiter(name = "apiRateLimiter")
	@Override
    public SuccessResponse parkCar(String licensePlate) {
        Car newCar = new Car(licensePlate); // Create a new Car object
        logger.debug("Attempting to park car with license plate: {}", licensePlate);

        for (int i = 1; i <= size; i++) {
            if (slots.containsValue(newCar)) {
            	String message = String.format("Car with license plate %s is already parked.", licensePlate);
                logger.warn(message);
                throw new AlreadyParkedException(message);
            }

            Car parkedCar = slots.computeIfAbsent(i, k -> newCar );

            if (parkedCar == newCar) {
            	String message = String.format("Car with license plate %s parked in slot %d.", licensePlate, i);
                logger.info(message);
                return new SuccessResponse(message);
            }
        }
        String message = String.format("Parking lot is full; unable to park car with license plate %s.", licensePlate);
        logger.error(message);
        throw new ParkingLotFullException(licensePlate); // No available slots
    }

    /**
     * Unparks a car with the given license plate from the parking lot.
     *
     * @param licensePlate the license plate of the car to unpark
     * @return a message indicating the result of the unparking attempt
     */
    @RateLimiter(name = "apiRateLimiter")
    @Override
    public SuccessResponse unparkCar(String licensePlate) throws CarNotFoundException {
        logger.debug("Attempting to unpark car with license plate: {}", licensePlate);

        for (Map.Entry<Integer, Car> entry : slots.entrySet()) {
            if (entry.getValue() != null && entry.getValue().getLicensePlate().equals(licensePlate)) {
                slots.remove(entry.getKey());
                String message = String.format("Car with license plate %s removed from slot %d.", licensePlate, entry.getKey());
                logger.info(message);
                return new SuccessResponse(message);
            }
        }

        logger.warn("Car with license plate {} not found in parking lot.", licensePlate);
        throw new CarNotFoundException("Car with license plate " + licensePlate + " not found.");
        // If the car is not found
    }

    /**
     * Retrieves information about a specific parking slot.
     *
     * @param slot the number of the parking slot
     * @return a message indicating whether the slot is empty or occupied
     */
    @RateLimiter(name = "apiRateLimiter")
    @Override
    public SuccessResponse getSlotInfo(int slot) {
    	if(slot<=size) {
	        logger.debug("Retrieving information for slot: {}", slot);
	        Car car = slots.get(slot);
	        String message;
	
	        if (car == null) {
	            message = String.format("Slot %d is empty.", slot);
	            logger.info(message);
	        } else {
	            message = String.format("Slot %d is occupied by car with license plate %s.", slot, car.getLicensePlate());
	            logger.info(message);
	        }
	        
	        return new SuccessResponse(message);
    	} else {
    		 String message = String.format("Invalid Slot number %d", slot);
            logger.info(message);
    		throw new InvalidSlotNumberException(message);
    	}
    }
}
