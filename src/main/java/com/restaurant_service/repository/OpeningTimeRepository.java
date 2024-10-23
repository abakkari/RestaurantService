package com.restaurant_service.repository;

import com.restaurant_service.bo.OpeningTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpeningTimeRepository extends JpaRepository<OpeningTime, Long> {
}
