package com.restaurant_service.controller;

import com.restaurant_service.dto.AvailabilityDto;
import com.restaurant_service.service.AvailabilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AvailabilityControllerTest {

    @Mock
    private AvailabilityService availabilityService;

    @InjectMocks
    private AvailabilityController availabilityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAvailability_ReturnsCreatedStatus() throws Exception {
        AvailabilityDto availabilityDto = new AvailabilityDto();
        when(availabilityService.createAvailability(any(AvailabilityDto.class))).thenReturn(availabilityDto);

        ResponseEntity<AvailabilityDto> response = availabilityController.createAvailability(availabilityDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(availabilityDto, response.getBody());
    }

    @Test
    void deleteAvailability_ReturnsOkStatus() {
        Long id = 1L;
        doNothing().when(availabilityService).deleteAvailability(id);

        ResponseEntity<String> response = availabilityController.deleteAvailability(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Reservation canceled", response.getBody());
    }

    @Test
    void isRestaurantAvailable_ReturnsTrue() {
        Long restaurantId = 1L;
        LocalDate availabilityDate = LocalDate.now();
        LocalTime startTime = LocalTime.of(12, 0);
        LocalTime endTime = LocalTime.of(14, 0);
        when(availabilityService.isRestaurantAvailable(restaurantId, availabilityDate, startTime, endTime)).thenReturn(true);

        ResponseEntity<Boolean> response = availabilityController.isRestaurantAvailable(restaurantId, availabilityDate, startTime, endTime);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    void isRestaurantAvailable_ReturnsFalse() {
        Long restaurantId = 1L;
        LocalDate availabilityDate = LocalDate.now();
        LocalTime startTime = LocalTime.of(12, 0);
        LocalTime endTime = LocalTime.of(14, 0);
        when(availabilityService.isRestaurantAvailable(restaurantId, availabilityDate, startTime, endTime)).thenReturn(false);

        ResponseEntity<Boolean> response = availabilityController.isRestaurantAvailable(restaurantId, availabilityDate, startTime, endTime);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody());
    }
}