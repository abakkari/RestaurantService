package com.restaurant_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantTablesDto {
    private Long id;
    private int tableNumber;
    private int numberOfSeats;
    private Long restaurantId;

}
