package com.restaurant_service.service;

import com.restaurant_service.bo.Restaurant;
import com.restaurant_service.dto.RestaurantDto;
import com.restaurant_service.mapper.RestaurantMapper;
import com.restaurant_service.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing restaurants.
 */
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService{

    private final RestaurantRepository restaurantRepository;

    private final RestaurantMapper restaurantMapper;

    /**
     * Creates a new restaurant.
     *
     * @param restaurantDto the restaurant data transfer object
     * @return the created restaurant data transfer object
     */
    @Override
    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDto.getName());
        restaurant.setEmail(restaurantDto.getEmail());
        restaurant.setPhoneNumber(restaurantDto.getPhoneNumber());
        restaurant.setAddress(restaurantDto.getAddress());

        restaurantRepository.save(restaurant);

        return restaurantMapper.toRestaurantDto(restaurant);
    }

    /**
     * Updates an existing restaurant.
     *
     * @param restaurantDto the restaurant data transfer object
     * @return the updated restaurant data transfer object
     * @throws Exception if the restaurant is not found
     */
    @Override
    public RestaurantDto updateRestaurant(RestaurantDto restaurantDto) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(restaurantDto.getId()).orElse(null);
        if(restaurant != null){
            restaurant.setName(restaurantDto.getName());
            restaurant.setEmail(restaurantDto.getEmail());
            restaurant.setPhoneNumber(restaurantDto.getPhoneNumber());
            restaurant.setAddress(restaurantDto.getAddress());
            restaurantRepository.save(restaurant);

            return restaurantMapper.toRestaurantDto(restaurant);
        } else {
            // to be changed to a custom exception and not a genric one
            throw new Exception("Restaurant not found");
        }
    }

    /**
     * Deletes a restaurant by ID.
     *
     * @param id the ID of the restaurant to delete
     * @throws Exception if the restaurant is not found
     */
    @Override
    public void deleteRestaurant(Long id) throws Exception {
        restaurantRepository.deleteById(id);
    }

    /**
     * Retrieves all restaurants.
     *
     * @return a list of restaurant data transfer objects
     */
    @Override
    public List<RestaurantDto> getRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurantMapper.toRestaurantDtos(restaurants);
    }

    /**
     * Retrieves a restaurant DTO by its ID.
     *
     * @param restaurantId the ID of the restaurant
     * @return the restaurant data transfer object
     */
    @Override
    public RestaurantDto getRestaurantDtoById(Long restaurantId){
        return restaurantMapper.toRestaurantDto(restaurantRepository.findById(restaurantId).orElse(null));
    }

    /**
     * Retrieves a restaurant by its ID.
     *
     * @param restaurantId the ID of the restaurant
     * @return the restaurant, or null if not found
     */
    @Override
    public Restaurant getRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElse(null);
    }

    /**
     * Retrieves a restaurant by name.
     *
     * @param restaurantName the name of the restaurant
     * @return the restaurant data transfer object
     * @throws Exception if the restaurant is not found
     */
    @Override
    public RestaurantDto getRestaurantByName(String restaurantName) throws Exception {
        // Exception to be changed to a custom exception and not a genric one
        return restaurantMapper.toRestaurantDto(restaurantRepository.findByName(restaurantName)
                .orElseThrow(() -> new Exception("Restaurant not found with name : " + restaurantName)));
    }
}
