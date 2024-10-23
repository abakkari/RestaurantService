package com.restaurant_service.controller;

import com.restaurant_service.dto.RestaurantTablesDto;
import com.restaurant_service.service.RestaurantTablesService;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestaurantTablesControllerTest {

    @Mock
    private RestaurantTablesService restaurantTablesService;

    @InjectMocks
    private RestaurantTablesController restaurantTablesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRestaurantTables_ReturnsCreatedRestaurantTable(){
        RestaurantTablesDto restaurantTablesDto = new RestaurantTablesDto();
        RestaurantTablesDto createdRestaurantTable = new RestaurantTablesDto();
        when(restaurantTablesService.createRestaurantTables(any(RestaurantTablesDto.class))).thenReturn(createdRestaurantTable);

        ResponseEntity<RestaurantTablesDto> response = restaurantTablesController.createRestaurantTables(restaurantTablesDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdRestaurantTable, response.getBody());
    }

    @Test
    void getRestaurantTablesByRestaurantId_ReturnsListOfRestaurantTables() throws Exception {
        Long restaurantId = 1L;
        List<RestaurantTablesDto> restaurantTables = Arrays.asList(new RestaurantTablesDto(), new RestaurantTablesDto());
        when(restaurantTablesService.getRestaurantTablesByRestaurantId(restaurantId)).thenReturn(restaurantTables);

        ResponseEntity<List<RestaurantTablesDto>> response = restaurantTablesController.getRestaurantTablesByRestaurantId(restaurantId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurantTables, response.getBody());
    }

    @Test
    void getRestaurantTablesByRestaurantId_ThrowsException() throws Exception {
        Long restaurantId = 1L;
        when(restaurantTablesService.getRestaurantTablesByRestaurantId(restaurantId)).thenThrow(new Exception("Error"));

        Exception exception = assertThrows(Exception.class, () -> {
            restaurantTablesController.getRestaurantTablesByRestaurantId(restaurantId);
        });

        assertEquals("Error", exception.getMessage());
    }
}