package com.restaurant_service.service;

import com.restaurant_service.bo.Restaurant;
import com.restaurant_service.dto.RestaurantDto;
import com.restaurant_service.mapper.RestaurantMapper;
import com.restaurant_service.repository.RestaurantRepository;
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

class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRestaurant_ReturnsCreatedRestaurantDto() {
        RestaurantDto restaurantDto = new RestaurantDto();
        Restaurant restaurant = new Restaurant();
        when(restaurantMapper.toRestaurantDto(any(Restaurant.class))).thenReturn(restaurantDto);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        RestaurantDto result = restaurantService.createRestaurant(restaurantDto);

        assertEquals(restaurantDto, result);
    }

    @Test
    void updateRestaurant_ReturnsUpdatedRestaurantDto() throws Exception {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setId(1L);
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findById(restaurantDto.getId())).thenReturn(Optional.of(restaurant));
        when(restaurantMapper.toRestaurantDto(any(Restaurant.class))).thenReturn(restaurantDto);

        RestaurantDto result = restaurantService.updateRestaurant(restaurantDto);

        assertEquals(restaurantDto, result);
    }

    @Test
    void updateRestaurant_ThrowsExceptionWhenNotFound() {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setId(1L);
        when(restaurantRepository.findById(restaurantDto.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            restaurantService.updateRestaurant(restaurantDto);
        });

        assertEquals("Restaurant not found", exception.getMessage());
    }

    @Test
    void deleteRestaurant_DeletesRestaurant() throws Exception {
        Long id = 1L;
        doNothing().when(restaurantRepository).deleteById(id);

        restaurantService.deleteRestaurant(id);

        verify(restaurantRepository, times(1)).deleteById(id);
    }

    @Test
    void getRestaurants_ReturnsListOfRestaurantDtos() {
        List<Restaurant> restaurants = Arrays.asList(new Restaurant(), new Restaurant());
        List<RestaurantDto> restaurantDtos = Arrays.asList(new RestaurantDto(), new RestaurantDto());
        when(restaurantRepository.findAll()).thenReturn(restaurants);
        when(restaurantMapper.toRestaurantDtos(restaurants)).thenReturn(restaurantDtos);

        List<RestaurantDto> result = restaurantService.getRestaurants();

        assertEquals(restaurantDtos, result);
    }

    @Test
    void getRestaurantById_ReturnsRestaurantDto() throws Exception {
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        RestaurantDto restaurantDto = new RestaurantDto();
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(restaurantMapper.toRestaurantDto(restaurant)).thenReturn(restaurantDto);

        RestaurantDto result = restaurantService.getRestaurantDtoById(restaurantId);

        assertEquals(restaurantDto, result);
    }

    @Test
    void getRestaurantByName_ReturnsRestaurantDto() throws Exception {
        String name = "Test Restaurant";
        Restaurant restaurant = new Restaurant();
        RestaurantDto restaurantDto = new RestaurantDto();
        when(restaurantRepository.findByName(name)).thenReturn(Optional.of(restaurant));
        when(restaurantMapper.toRestaurantDto(restaurant)).thenReturn(restaurantDto);

        RestaurantDto result = restaurantService.getRestaurantByName(name);

        assertEquals(restaurantDto, result);
    }

    @Test
    void getRestaurantByName_ThrowsExceptionWhenNotFound() {
        String name = "Test Restaurant";
        when(restaurantRepository.findByName(name)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            restaurantService.getRestaurantByName(name);
        });

        assertEquals("Restaurant not found with name : " + name, exception.getMessage());
    }
}