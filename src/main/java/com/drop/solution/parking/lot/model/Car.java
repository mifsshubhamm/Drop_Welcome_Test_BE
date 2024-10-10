package com.drop.solution.parking.lot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a car with a license plate.
 * This class provides methods for comparing car objects based on their license plates.
 */
@AllArgsConstructor
@Getter
public class Car {
    private static final Logger logger = LoggerFactory.getLogger(Car.class);
    private String licensePlate; // The license plate of the car

    /**
     * Checks if this car is equal to another object.
     *
     * @param obj the object to compare with this car
     * @return true if the given object is a Car and has the same license plate; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            logger.debug("Comparing with the same object reference."); // Log reference equality
            return true; // Check for reference equality
        }
        if (!(obj instanceof Car)) {
            logger.debug("Object is not an instance of Car."); // Log type mismatch
            return false; // Check if obj is an instance of Car
        }
        Car other = (Car) obj; // Cast to Car
        boolean isEqual = licensePlate.equals(other.licensePlate); // Compare license plates
        logger.info("Comparing license plates: {} and {} - Equal: {}", licensePlate, other.licensePlate, isEqual);
        return isEqual; // Return comparison result
    }

    /**
     * Returns the hash code value for this car.
     *
     * @return the hash code based on the license plate
     */
    @Override
    public int hashCode() {
        int hash = licensePlate.hashCode(); // Use license plate for hash code
        logger.debug("Hash code for license plate {}: {}", licensePlate, hash);
        return hash; // Return hash code
    }
}
