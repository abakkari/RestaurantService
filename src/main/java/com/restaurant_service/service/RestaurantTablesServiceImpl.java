package com.restaurant_service.service;

import com.restaurant_service.bo.RestaurantTables;
import com.restaurant_service.dto.RestaurantTablesDto;
import com.restaurant_service.mapper.RestaurantTablesMapper;
import com.restaurant_service.repository.RestaurantTablesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing restaurant tables.
 */
@Service
@RequiredArgsConstructor
public class RestaurantTablesServiceImpl implements RestaurantTablesService {

    private final RestaurantTablesRepository restaurantTablesRepository;
    private final RestaurantService restaurantService;
    private final RestaurantTablesMapper restaurantTablesDtoMapper;

    /**
     * Creates a new restaurant table entry.
     *
     * @param restaurantTablesDto the restaurant tables data transfer object
     * @return the created restaurant tables data transfer object
     */
    @Override
    public RestaurantTablesDto createRestaurantTables(RestaurantTablesDto restaurantTablesDto) {
        RestaurantTables restaurantTable = new RestaurantTables();
        restaurantTable.setTableNumber(restaurantTablesDto.getTableNumber());
        restaurantTable.setNumberOfSeats(restaurantTablesDto.getNumberOfSeats());
        restaurantTable.setRestaurant(restaurantService.getRestaurantById(restaurantTablesDto.getRestaurantId()));
        restaurantTablesRepository.save(restaurantTable);

        return restaurantTablesDtoMapper.toRestaurantTablesDto(restaurantTable);    }

    /**
     * Retrieves a restaurant table by ID.
     *
     * @param id the ID of the restaurant table
     * @return the restaurant table business object
     * @throws Exception if the restaurant table is not found
     */
    @Override
    public RestaurantTables getRestaurantTableById(Long id) throws Exception {
        // Exception to be changed to a custom exception and not a genric one
        return restaurantTablesRepository.findById(id).orElseThrow(() -> new Exception("No table found with id : " + id));
    }

    /**
     * Retrieves restaurant tables by restaurant ID.
     *
     * @param restaurantId the ID of the restaurant
     * @return a list of restaurant tables data transfer objects
     * @throws Exception if no tables are found for the given restaurant ID
     */
    @Override
    public List<RestaurantTablesDto> getRestaurantTablesByRestaurantId(Long restaurantId) throws Exception {
        // Exception to be changed to a custom exception and not a genric one
        return restaurantTablesDtoMapper.toRestaurantTablesDtos(restaurantTablesRepository.findByRestaurantId(restaurantId)
                .orElseThrow(() -> new Exception("No table found with Restaurant id : " + restaurantId)));
    }
}
