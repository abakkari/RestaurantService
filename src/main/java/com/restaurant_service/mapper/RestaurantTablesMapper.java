package com.restaurant_service.mapper;

import com.restaurant_service.bo.RestaurantTables;
import com.restaurant_service.dto.RestaurantTablesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantTablesMapper {

    @Mapping(target = "restaurantId", source = "restaurant.id")
    RestaurantTablesDto toRestaurantTablesDto(RestaurantTables restaurantTables);

    List<RestaurantTablesDto> toRestaurantTablesDtos(List<RestaurantTables> restaurantTables);
}
