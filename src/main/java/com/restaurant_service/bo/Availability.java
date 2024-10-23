package com.restaurant_service.bo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "table_id")
    private RestaurantTables restaurantTables;
    private LocalDate availabilityDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private long reservationId;
}
