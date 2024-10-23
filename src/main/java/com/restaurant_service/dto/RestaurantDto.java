package com.restaurant_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDto {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

}
