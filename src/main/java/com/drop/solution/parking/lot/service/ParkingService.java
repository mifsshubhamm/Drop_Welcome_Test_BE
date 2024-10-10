package com.drop.solution.parking.lot.service;


import com.drop.solution.parking.lot.exception.AlreadyParkedException;
import com.drop.solution.parking.lot.exception.CarNotFoundException;
import com.drop.solution.parking.lot.exception.ParkingLotFullException;
import com.drop.solution.parking.lot.model.SuccessResponse;

/**
 * Interface representing a parking service for managing car parking operations.
 * This interface provides methods to park and unpark cars, as well as to retrieve
 * information about specific parking slots.
 */
public interface ParkingService {

    /**
     * Parks a car in the parking lot using the specified license plate.
     *
     * @param licensePlate the license plate of the car to be parked
     * @return a SuccessResponse indicating the result of the parking operation
     *         (e.g., success message, allocated slot number)
     * @throws AlreadyParkedException if the car with the given license plate is already parked
     *         in the parking lot
     * @throws ParkingLotFullException if there are no available parking slots
     */
    SuccessResponse parkCar(String licensePlate);

    /**
     * Unparks a car from the parking lot using the specified license plate.
     *
     * @param licensePlate the license plate of the car to be unparked
     * @return a SuccessResponse indicating the result of the unparking operation
     *         (e.g., success message, slot number from which the car was removed)
     * @throws CarNotFoundException if there is no car with the given license plate parked
     *         in the parking lot
     */
    SuccessResponse unparkCar(String licensePlate) throws CarNotFoundException;

    /**
     * Retrieves information about a specific parking slot.
     *
     * @param slot the number of the parking slot to check
     * @return a SuccessResponse indicating whether the slot is empty or occupied,
     *         along with any relevant details (e.g., license plate of the parked car)
     */
    SuccessResponse getSlotInfo(int slot);
}
