package com.restaurant_service.controller;

import com.restaurant_service.dto.RestaurantDto;
import com.restaurant_service.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing restaurants.
 */
@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    /**
     * Creates a new restaurant.
     *
     * @param restaurantDto the restaurant data transfer object
     * @return a response entity with the created restaurant and HTTP status
     */
    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto restaurantDto) {
        RestaurantDto createdRestaurant = restaurantService.createRestaurant(restaurantDto);
        return new ResponseEntity<>(createdRestaurant, HttpStatus.CREATED);
    }

    /**
     * Updates an existing restaurant.
     *
     * @param restaurantDto the restaurant data transfer object
     * @return a response entity with the updated restaurant and HTTP status
     * @throws Exception if an error occurs during the update
     */
    @PutMapping("/update")
    public ResponseEntity<RestaurantDto> updateRestaurant(@RequestBody RestaurantDto restaurantDto) throws Exception {
        // Exception to be changed to a custom exception and not a genric one
        RestaurantDto updatedRestaurant = restaurantService.updateRestaurant(restaurantDto);
        return new ResponseEntity<>(updatedRestaurant, HttpStatus.OK);
    }

    /**
     * Deletes a restaurant by ID.
     *
     * @param id the ID of the restaurant to delete
     * @return a response entity with the deletion status
     * @throws Exception if an error occurs during deletion
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long id) throws Exception {
        // Exception to be changed to a custom exception and not a genric one
       restaurantService.deleteRestaurant(id);
       return new ResponseEntity<>("Restaurant deleted", HttpStatus.OK);
    }

    /**
     * Retrieves all restaurants.
     *
     * @return a response entity with the list of all restaurants and HTTP status
     */
    @GetMapping("/all")
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        List<RestaurantDto> restaurants = restaurantService.getRestaurants();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    /**
     * Retrieves a restaurant by ID.
     *
     * @param id the ID of the restaurant to retrieve
     * @return a response entity with the restaurant and HTTP status
     */
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable Long id) {
        RestaurantDto restaurant = restaurantService.getRestaurantDtoById(id);
        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}
