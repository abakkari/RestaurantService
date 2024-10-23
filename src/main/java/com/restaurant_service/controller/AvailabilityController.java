package com.restaurant_service.controller;

import com.restaurant_service.dto.AvailabilityDto;
import com.restaurant_service.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * REST controller for managing availability of restaurant tables.
 */
@RestController
@RequestMapping("/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    /**
     * Creates a new availability entry.
     *
     * @param availabilityDto the availability data transfer object
     * @return a response entity with the creation status
     * @throws Exception if an error occurs during creation
     */
    @PostMapping
    public ResponseEntity<AvailabilityDto> createAvailability(@RequestBody AvailabilityDto availabilityDto) throws Exception {
        // Exception to be changed to a custom exception and not a genric one
        AvailabilityDto response = availabilityService.createAvailability(availabilityDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    /**
     * Deletes an availability entry by Reservation ID.
     *
     * @param id the ID of the availability entry to delete
     * @return a response entity with the deletion status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAvailability(@PathVariable Long id) {
        availabilityService.deleteAvailability(id);
        return new ResponseEntity<>("Reservation canceled", HttpStatus.OK);
    }

    /**
     * Gets an availability entry by ID.
     *
     * @param id the ID of the availability entry
     * @return a response entity with the availability entry
     */
    @GetMapping("/{id}")
    public ResponseEntity<AvailabilityDto> getAvailabilityById(@PathVariable Long id) {
        AvailabilityDto availabilityDto = availabilityService.getAvailabilityById(id);
        if (availabilityDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(availabilityDto, HttpStatus.OK);
    }

    /**
     * Checks if a restaurant is available for a given date and time range.
     *
     * @param tableId the ID of the restaurant table
     * @param availabilityDate the date of availability
     * @param startTime the start time of availability
     * @param endTime the end time of availability
     * @return a response entity with the availability status
     */
    @GetMapping("/is-available")
    public ResponseEntity<Boolean> isRestaurantAvailable(@RequestParam Long tableId,
                                                         @RequestParam LocalDate availabilityDate,
                                                         @RequestParam LocalTime startTime,
                                                         @RequestParam LocalTime endTime) {
        boolean isAvailable = availabilityService.isRestaurantAvailable(tableId, availabilityDate, startTime, endTime);
        return new ResponseEntity<>(isAvailable, HttpStatus.OK);
    }
}
