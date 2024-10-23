package com.restaurant_service.service;

import com.restaurant_service.bo.Restaurant;
import com.restaurant_service.dto.RestaurantDto;

import java.util.List;

public interface RestaurantService {

    RestaurantDto createRestaurant(RestaurantDto restaurant);

    RestaurantDto updateRestaurant(RestaurantDto restaurant) throws Exception;

    void deleteRestaurant(Long id) throws Exception;

    List<RestaurantDto> getRestaurants();

    Restaurant getRestaurantById(Long restaurantId);

    RestaurantDto getRestaurantDtoById(Long restaurantId);

    RestaurantDto getRestaurantByName(String restaurantName) throws Exception;




}
