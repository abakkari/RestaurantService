package com.restaurant_service.service;

import com.restaurant_service.bo.RestaurantTables;
import com.restaurant_service.dto.RestaurantTablesDto;

import java.util.List;

public interface RestaurantTablesService {
    RestaurantTablesDto createRestaurantTables(RestaurantTablesDto tableDto);

    RestaurantTables getRestaurantTableById(Long id) throws Exception;

    List<RestaurantTablesDto> getRestaurantTablesByRestaurantId(Long restaurantId) throws Exception;

}
