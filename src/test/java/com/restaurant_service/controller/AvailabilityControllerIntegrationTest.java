package com.restaurant_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant_service.bo.Restaurant;
import com.restaurant_service.bo.RestaurantTables;
import com.restaurant_service.dto.AvailabilityDto;
import com.restaurant_service.repository.RestaurantRepository;
import com.restaurant_service.repository.RestaurantTablesRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AvailabilityControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantTablesRepository restaurantTablesRepository;

    @BeforeAll
    void setup() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");
        restaurantRepository.save(restaurant);

        RestaurantTables table = new RestaurantTables();
        table.setId(1L);
        table.setRestaurant(restaurant);
        restaurantTablesRepository.save(table);
    }

    @Test
    void testCreateAvailability() throws Exception {
        AvailabilityDto availabilityDto = new AvailabilityDto();
        availabilityDto.setTableId(1L);
        availabilityDto.setAvailabilityDate(LocalDate.of(2024, 10, 25));
        availabilityDto.setStartTime(LocalTime.of(10, 10, 0));
        availabilityDto.setEndTime(LocalTime.of(12, 10, 0));
        availabilityDto.setReservationId(1L);

        String availabilityJson = objectMapper.writeValueAsString(availabilityDto);

        mockMvc.perform(post("/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(availabilityJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.availabilityDate").value("2024-10-25"))
                .andExpect(jsonPath("$.startTime").value("10:10:00"))
                .andExpect(jsonPath("$.endTime").value("12:10:00"));
    }

    @Test
    void testDeleteAvailability() throws Exception {
        AvailabilityDto availabilityDto = new AvailabilityDto();
        availabilityDto.setTableId(1L);
        availabilityDto.setAvailabilityDate(LocalDate.of(2025, 10, 25));
        availabilityDto.setStartTime(LocalTime.of(10, 10, 0));
        availabilityDto.setEndTime(LocalTime.of(12, 10, 0));
        availabilityDto.setReservationId(1L);


        String availabilityJson = objectMapper.writeValueAsString(availabilityDto);

        String response = mockMvc.perform(post("/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(availabilityJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();


        Long availabilityId = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/availability/{id}", availabilityId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/availability/{id}", availabilityId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
