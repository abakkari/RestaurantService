package com.restaurant_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class AvailabilityDto {
    private Long id;
    private LocalDate availabilityDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long reservationId;
    private Long tableId;
}