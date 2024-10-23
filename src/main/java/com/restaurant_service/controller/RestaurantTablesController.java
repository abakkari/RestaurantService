package com.restaurant_service.controller;

import com.restaurant_service.dto.RestaurantTablesDto;
import com.restaurant_service.service.RestaurantTablesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing restaurant tables.
 */
@RestController
@RequestMapping("/restaurant-table")
@RequiredArgsConstructor
public class RestaurantTablesController {

    private final RestaurantTablesService restaurantTablesService;

    /**
     * Creates a new restaurant table entry.
     *
     * @param restaurantTablesDto the restaurant tables data transfer object
     * @return a response entity with the created restaurant table and HTTP status
     * @throws Exception if an error occurs during creation
     */
    @PostMapping
    public ResponseEntity<RestaurantTablesDto> createRestaurantTables(@RequestBody RestaurantTablesDto restaurantTablesDto) {
        RestaurantTablesDto createdRestaurantTable = restaurantTablesService.createRestaurantTables(restaurantTablesDto);
        return new ResponseEntity<>(createdRestaurantTable, HttpStatus.CREATED);
    }

    /**
     * Retrieves restaurant tables by restaurant ID.
     *
     * @param restaurantId the ID of the restaurant
     * @return a response entity with the list of restaurant tables and HTTP status
     * @throws Exception if an error occurs during retrieval
     */
    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<RestaurantTablesDto>> getRestaurantTablesByRestaurantId(@PathVariable Long restaurantId) throws Exception {
        // Exception to be changed to a custom exception and not a genric one
        List<RestaurantTablesDto> restaurantTables = restaurantTablesService.getRestaurantTablesByRestaurantId(restaurantId);
        return new ResponseEntity<>(restaurantTables, HttpStatus.OK);
    }
}
