package com.restaurant_service.repository;

import com.restaurant_service.bo.RestaurantTables;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantTablesRepository extends JpaRepository<RestaurantTables, Long> {

    Optional<List<RestaurantTables>> findByRestaurantId(Long restaurantId);
}
