package com.restaurant_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant_service.dto.RestaurantDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RestaurantControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateRestaurant() throws Exception {
        RestaurantDto restaurantDTO = new RestaurantDto();
        restaurantDTO.setName("Restaurant Test");
        restaurantDTO.setAddress("123 rue de Test");
        restaurantDTO.setPhoneNumber("0102030405");

        String restaurantJson = objectMapper.writeValueAsString(restaurantDTO);

        mockMvc.perform(post("/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restaurantJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Restaurant Test"))
                .andExpect(jsonPath("$.address").value("123 rue de Test"))
                .andExpect(jsonPath("$.phoneNumber").value("0102030405"));
    }

    @Test
    void testGetAllRestaurants() throws Exception {
        mockMvc.perform(get("/restaurant/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))))
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void testGetRestaurantById() throws Exception {
        RestaurantDto restaurantDTO = new RestaurantDto();
        restaurantDTO.setName("Restaurant Test 2");
        restaurantDTO.setAddress("123 rue de Test 2");
        restaurantDTO.setPhoneNumber("0102030405");


        String restaurantJson = objectMapper.writeValueAsString(restaurantDTO);

        String response = mockMvc.perform(post("/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restaurantJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long restaurantId = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/restaurant/{id}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Restaurant Test 2"))
                .andExpect(jsonPath("$.address").value("123 rue de Test 2"))
                .andExpect(jsonPath("$.phoneNumber").value("0102030405"));
    }

    @Test
    void testDeleteRestaurant() throws Exception {
        RestaurantDto restaurantDTO = new RestaurantDto();
        restaurantDTO.setName("Restaurant Test 2");
        restaurantDTO.setAddress("456 rue de Test");
        restaurantDTO.setPhoneNumber("0102030405");

        String restaurantJson = objectMapper.writeValueAsString(restaurantDTO);

        String response = mockMvc.perform(post("/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restaurantJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long restaurantId = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/restaurant/delete/{id}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(get("/restaurant/{id}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

