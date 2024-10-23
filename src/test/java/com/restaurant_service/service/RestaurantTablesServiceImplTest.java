package com.restaurant_service.service;

import com.restaurant_service.bo.Restaurant;
import com.restaurant_service.bo.RestaurantTables;
import com.restaurant_service.dto.RestaurantTablesDto;
import com.restaurant_service.mapper.RestaurantTablesMapper;
import com.restaurant_service.repository.RestaurantTablesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestaurantTablesServiceImplTest {

    @Mock
    private RestaurantTablesRepository restaurantTablesRepository;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private RestaurantTablesMapper restaurantTablesDtoMapper;

    @InjectMocks
    private RestaurantTablesServiceImpl restaurantTablesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRestaurantTables_ReturnsCreatedRestaurantTablesDto() throws Exception {
        RestaurantTablesDto restaurantTablesDto = new RestaurantTablesDto();
        RestaurantTables restaurantTable = new RestaurantTables();
        when(restaurantService.getRestaurantById(any(Long.class))).thenReturn(new Restaurant());
        when(restaurantTablesRepository.save(any(RestaurantTables.class))).thenReturn(restaurantTable);
        when(restaurantTablesDtoMapper.toRestaurantTablesDto(any(RestaurantTables.class))).thenReturn(restaurantTablesDto);

        RestaurantTablesDto result = restaurantTablesService.createRestaurantTables(restaurantTablesDto);

        assertEquals(restaurantTablesDto, result);
    }

    @Test
    void getRestaurantTableById_ReturnsRestaurantTable() throws Exception {
        Long id = 1L;
        RestaurantTables restaurantTable = new RestaurantTables();
        when(restaurantTablesRepository.findById(id)).thenReturn(Optional.of(restaurantTable));

        RestaurantTables result = restaurantTablesService.getRestaurantTableById(id);

        assertEquals(restaurantTable, result);
    }

    @Test
    void getRestaurantTableById_ThrowsExceptionWhenNotFound() {
        Long id = 1L;
        when(restaurantTablesRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            restaurantTablesService.getRestaurantTableById(id);
        });

        assertEquals("No table found with id : " + id, exception.getMessage());
    }

    @Test
    void getRestaurantTablesByRestaurantId_ReturnsListOfRestaurantTablesDtos() throws Exception {
        Long restaurantId = 1L;
        List<RestaurantTables> restaurantTables = Arrays.asList(new RestaurantTables(), new RestaurantTables());
        List<RestaurantTablesDto> restaurantTablesDtos = Arrays.asList(new RestaurantTablesDto(), new RestaurantTablesDto());
        when(restaurantTablesRepository.findByRestaurantId(restaurantId)).thenReturn(Optional.of(restaurantTables));
        when(restaurantTablesDtoMapper.toRestaurantTablesDtos(restaurantTables)).thenReturn(restaurantTablesDtos);

        List<RestaurantTablesDto> result = restaurantTablesService.getRestaurantTablesByRestaurantId(restaurantId);

        assertEquals(restaurantTablesDtos, result);
    }

    @Test
    void getRestaurantTablesByRestaurantId_ThrowsExceptionWhenNotFound() {
        Long restaurantId = 1L;
        when(restaurantTablesRepository.findByRestaurantId(restaurantId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            restaurantTablesService.getRestaurantTablesByRestaurantId(restaurantId);
        });

        assertEquals("No table found with Restaurant id : " + restaurantId, exception.getMessage());
    }
}