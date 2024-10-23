package com.restaurant_service.mapper;

import com.restaurant_service.bo.Restaurant;
import com.restaurant_service.dto.RestaurantDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantDto toRestaurantDto(Restaurant restaurant);

    List<RestaurantDto> toRestaurantDtos(List<Restaurant> restaurants);

}