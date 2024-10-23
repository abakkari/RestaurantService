package com.restaurant_service.service;

import com.restaurant_service.bo.Availability;
import com.restaurant_service.bo.Restaurant;
import com.restaurant_service.bo.RestaurantTables;
import com.restaurant_service.dto.AvailabilityDto;
import com.restaurant_service.mapper.AvailabilityMapper;
import com.restaurant_service.repository.AvailabilityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AvailabilityServiceImplTest {

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private RestaurantTablesService restaurantTablesService;

    @Mock
    private AvailabilityMapper availabilityMapper;

    @InjectMocks
    private AvailabilityServiceImpl availabilityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAvailability_ReturnsAvailabilityCreated() throws Exception {
        AvailabilityDto availabilityDto = new AvailabilityDto();
        availabilityDto.setTableId(1L);
        availabilityDto.setAvailabilityDate(LocalDate.now());
        availabilityDto.setStartTime(LocalTime.of(12, 0));
        availabilityDto.setEndTime(LocalTime.of(14, 0));
        availabilityDto.setReservationId(1L);

        RestaurantTables restaurantTables = new RestaurantTables();
        when(restaurantTablesService.getRestaurantTableById(any(Long.class))).thenReturn(restaurantTables);
        when(availabilityMapper.toAvailabilityDto(any(Availability.class))).thenReturn(availabilityDto);

        AvailabilityDto result = availabilityService.createAvailability(availabilityDto);

        assertEquals(availabilityDto, result);
    }

    @Test
    void isRestaurantAvailable_ReturnsTrue_WhenNoTablesReserved() {
        Long restaurantId = 1L;
        LocalDate availabilityDate = LocalDate.now();
        LocalTime startTime = LocalTime.of(12, 0);
        LocalTime endTime = LocalTime.of(14, 0);

        when(availabilityRepository.countTablesReservedByRestaurantIdAndDateTime(restaurantId, availabilityDate, startTime, endTime)).thenReturn(0L);

        boolean result = availabilityService.isRestaurantAvailable(restaurantId, availabilityDate, startTime, endTime);

        assertTrue(result);
    }

    @Test
    void isRestaurantAvailable_ReturnsFalse_WhenTablesReserved() {
        Long restaurantId = 1L;
        LocalDate availabilityDate = LocalDate.now();
        LocalTime startTime = LocalTime.of(12, 0);
        LocalTime endTime = LocalTime.of(14, 0);

        when(availabilityRepository.countTablesReservedByRestaurantIdAndDateTime(restaurantId, availabilityDate, startTime, endTime)).thenReturn(5L);

        boolean result = availabilityService.isRestaurantAvailable(restaurantId, availabilityDate, startTime, endTime);

        assertFalse(result);
    }

    @Test
    void deleteAvailability_DeletesAvailability() {
        Long id = 1L;
        doNothing().when(availabilityRepository).deleteById(id);

        availabilityService.deleteAvailability(id);

        verify(availabilityRepository, times(1)).deleteById(id);
    }
}