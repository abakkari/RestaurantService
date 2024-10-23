package com.restaurant_service.controller;

import com.restaurant_service.dto.RestaurantDto;
import com.restaurant_service.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestaurantControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRestaurant_ReturnsCreatedRestaurant() {
        RestaurantDto restaurantDto = new RestaurantDto();
        RestaurantDto createdRestaurant = new RestaurantDto();
        when(restaurantService.createRestaurant(any(RestaurantDto.class))).thenReturn(createdRestaurant);

        ResponseEntity<RestaurantDto> response = restaurantController.createRestaurant(restaurantDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdRestaurant, response.getBody());
    }

    @Test
    void updateRestaurant_ReturnsUpdatedRestaurant() throws Exception {
        RestaurantDto restaurantDto = new RestaurantDto();
        RestaurantDto updatedRestaurant = new RestaurantDto();
        when(restaurantService.updateRestaurant(any(RestaurantDto.class))).thenReturn(updatedRestaurant);

        ResponseEntity<RestaurantDto> response = restaurantController.updateRestaurant(restaurantDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedRestaurant, response.getBody());
    }

    @Test
    void deleteRestaurant_ReturnsDeletionMessage() throws Exception {
        Long id = 1L;
        doNothing().when(restaurantService).deleteRestaurant(id);

        ResponseEntity<String> response = restaurantController.deleteRestaurant(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Restaurant deleted", response.getBody());
    }

    @Test
    void getAllRestaurants_ReturnsListOfRestaurants() {
        List<RestaurantDto> restaurants = Arrays.asList(new RestaurantDto(), new RestaurantDto());
        when(restaurantService.getRestaurants()).thenReturn(restaurants);

        ResponseEntity<List<RestaurantDto>> response = restaurantController.getAllRestaurants();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurants, response.getBody());
    }

    @Test
    void getRestaurantById_ReturnsRestaurantDto() throws Exception {
        Long id = 1L;
        RestaurantDto restaurantDto = new RestaurantDto();
        when(restaurantService.getRestaurantDtoById(id)).thenReturn(restaurantDto);

        ResponseEntity<RestaurantDto> response = restaurantController.getRestaurantById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurantDto, response.getBody());
    }

}