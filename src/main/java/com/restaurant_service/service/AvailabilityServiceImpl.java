package com.restaurant_service.service;

import com.restaurant_service.bo.Availability;
import com.restaurant_service.dto.AvailabilityDto;
import com.restaurant_service.mapper.AvailabilityMapper;
import com.restaurant_service.repository.AvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Service implementation for managing availability of restaurant tables.
 */
@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final RestaurantTablesService restaurantTablesService;
    private final AvailabilityMapper availabilityMapper;

    /**
     * Creates a new availability entry.
     *
     * @param availabilityDto the availability data transfer object
     * @return a string message indicating the result of the operation
     * @throws Exception if an error occurs during creation
     */
    @Override
    public AvailabilityDto createAvailability(AvailabilityDto availabilityDto) throws Exception {
        if(restaurantTablesService.getRestaurantTableById(availabilityDto.getTableId()) != null) {
            Availability availability = new Availability();
            availability.setAvailabilityDate(availabilityDto.getAvailabilityDate());
            availability.setStartTime(availabilityDto.getStartTime());
            availability.setEndTime(availabilityDto.getEndTime());
            availability.setReservationId(availabilityDto.getReservationId());
            availability.setRestaurantTables(restaurantTablesService.getRestaurantTableById(availabilityDto.getTableId()));
            availabilityRepository.save(availability);

            return availabilityMapper.toAvailabilityDto(availability);
        } else {
            throw new Exception("Restaurant table not found");
        }
    }

    /**
     * Checks if a restaurant is available for a given date and time range.
     *
     * @param tableId the ID of the restaurant table
     * @param availabilityDate the date of availability
     * @param startTime the start time of availability
     * @param endTime the end time of availability
     * @return true if the restaurant is available, false otherwise
     */
    @Override
    public boolean isRestaurantAvailable(Long tableId, LocalDate availabilityDate,
                                                                      LocalTime startTime, LocalTime endTime) {
        Long count = availabilityRepository.countTablesReservedByRestaurantIdAndDateTime(tableId, availabilityDate, startTime, endTime);
        if(count == 0) {
            return true;
        } else {
            //TODO: Add Business logique to handle the case where the allready some tables reserved
            return false;
        }
    }

    /**
     * Deletes an availability entry by reservation ID.
     *
     * @param id the ID of the availability entry to delete
     */
    @Override
    public void deleteAvailability(Long id) {
        availabilityRepository.deleteById(id);
    }

    @Override
    public AvailabilityDto getAvailabilityById(Long id) {
        return availabilityMapper.toAvailabilityDto(availabilityRepository.findById(id).orElse(null));
    }
}