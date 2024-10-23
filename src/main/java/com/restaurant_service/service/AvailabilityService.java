package com.restaurant_service.service;

import com.restaurant_service.dto.AvailabilityDto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AvailabilityService {

    AvailabilityDto createAvailability(AvailabilityDto availabilityDto) throws Exception;
    boolean isRestaurantAvailable(Long tableId, LocalDate availabilityDate, LocalTime startTime, LocalTime endTime);

    void deleteAvailability(Long id);

    AvailabilityDto getAvailabilityById(Long id);
}
