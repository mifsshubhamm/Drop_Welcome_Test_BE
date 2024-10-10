package com.drop.solution.parking.lot;


import com.drop.solution.parking.lot.controller.ParkingController;
import com.drop.solution.parking.lot.model.CarDetailsRequest;
import com.drop.solution.parking.lot.model.SuccessResponse;
import com.drop.solution.parking.lot.service.ParkingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class ParkingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ParkingService parkingService;

    @InjectMocks
    private ParkingController parkingController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(parkingController).build();
    }

    @Test
    void testParkCar() throws Exception {
        CarDetailsRequest request = new CarDetailsRequest();
        request.setLicensePlate("UP78BX9207");
        
        SuccessResponse successResponse = new SuccessResponse("Car parked successfully.");
        
        when(parkingService.parkCar("UP78BX9207")).thenReturn(successResponse);

        mockMvc.perform(post("/api/parking/park")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"licensePlate\": \"UP78BX9207\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Car parked successfully."));
    }

    @Test
    void testGetSlot() throws Exception {
        int slot = 1;
        SuccessResponse successResponse = new SuccessResponse("Slot information.");

        when(parkingService.getSlotInfo(slot)).thenReturn(successResponse);

        mockMvc.perform(get("/api/parking/slot")
                .param("slot", String.valueOf(slot)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Slot information."));
    }

    @Test
    void testUnparkCar() throws Exception {
        String licensePlate = "UP78BX9207";
        SuccessResponse successResponse = new SuccessResponse("Car unparked successfully.");

        when(parkingService.unparkCar(licensePlate)).thenReturn(successResponse);

        mockMvc.perform(delete("/api/parking/unpark")
                .param("licensePlate", licensePlate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Car unparked successfully."));
    }
}