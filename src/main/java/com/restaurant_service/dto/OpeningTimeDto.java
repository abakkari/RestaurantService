package com.restaurant_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpeningTimeDto {
    private Long id;
    private String dayOfWeek;
    private String openingTime;
    private String closingTime;
    private Long restaurantId;
}