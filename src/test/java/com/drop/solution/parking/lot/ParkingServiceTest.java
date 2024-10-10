package com.drop.solution.parking.lot;

import com.drop.solution.parking.lot.exception.AlreadyParkedException;
import com.drop.solution.parking.lot.exception.CarNotFoundException;
import com.drop.solution.parking.lot.exception.InvalidSlotNumberException;
import com.drop.solution.parking.lot.exception.ParkingLotFullException;
import com.drop.solution.parking.lot.model.SuccessResponse;
import com.drop.solution.parking.lot.service.imp.ParkingServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ParkingServiceTest {

    @InjectMocks
    private ParkingServiceImp parkingService = new ParkingServiceImp(2);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testParkCarSuccessfully() {
        SuccessResponse response = parkingService.parkCar("UP78BX9207");
        System.out.print(response.getMessage());
        assertEquals("Car with license plate UP78BX9207 parked in slot 1.", response.getMessage());
    }

    @Test
    void testParkCarWhenAlreadyParked() {
        parkingService.parkCar("UP78BX9207");
        Exception exception = assertThrows(AlreadyParkedException.class, () -> {
            parkingService.parkCar("UP78BX9207");
        });
        assertEquals("Car with license plate UP78BX9207 is already parked.", exception.getMessage());
    }

    @Test
    void testParkCarWhenParkingLotIsFull() {
        parkingService.parkCar("UP78BX9207");
        parkingService.parkCar("UP78BX9288");

        Exception exception = assertThrows(ParkingLotFullException.class, () -> {
            parkingService.parkCar("UP16BX7632");
        });
        assertEquals("UP16BX7632", exception.getMessage());
    }

    @Test
    void testUnparkCarSuccessfully() throws CarNotFoundException {
        parkingService.parkCar("UP78BX9207");
        SuccessResponse response = parkingService.unparkCar("UP78BX9207");
        assertEquals("Car with license plate UP78BX9207 removed from slot 1.", response.getMessage());
    }

    @Test
    void testUnparkCarNotFound() {
        Exception exception = assertThrows(CarNotFoundException.class, () -> {
            parkingService.unparkCar("UP78BX9207");
        });
        assertEquals("Car with license plate UP78BX9207 not found.", exception.getMessage());
    }

    @Test
    void testGetSlotInfoWhenSlotIsEmpty() {
        SuccessResponse response = parkingService.getSlotInfo(1);
        assertEquals("Slot 1 is empty.", response.getMessage());
    }

    @Test
    void testGetSlotInfoWhenSlotIsOccupied() {
        parkingService.parkCar("UP78BX9207");
        SuccessResponse response = parkingService.getSlotInfo(1);
        assertEquals("Slot 1 is occupied by car with license plate UP78BX9207.", response.getMessage());
    }

    @Test
    void testGetSlotInfoForInvalidSlot() {
        // Assuming size is 2, let's check for slot 3
    	Exception exception = assertThrows(InvalidSlotNumberException.class, () -> {
    		parkingService.getSlotInfo(3);
        });
        assertEquals("Invalid Slot number 3", exception.getMessage());
    }
}
