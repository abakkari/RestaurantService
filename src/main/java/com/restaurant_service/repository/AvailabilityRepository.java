package com.restaurant_service.repository;

import com.restaurant_service.bo.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalTime;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    @Query("SELECT COUNT(a.id) FROM Availability a " +
            "JOIN a.restaurantTables rt " +
            "WHERE rt.id = :tableId AND a.availabilityDate = :availabilityDate " +
            "AND a.startTime = :startTime AND a.endTime = :endTime")
    Long countTablesReservedByRestaurantIdAndDateTime(@Param("tableId") Long tableId,
                                              @Param("availabilityDate") LocalDate availabilityDate,
                                              @Param("startTime") LocalTime startTime,
                                              @Param("endTime") LocalTime endTime);

}
