package com.kenzie.restaurant.randomizer.randomizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.restaurant.randomizer.IntegrationTest;
import com.kenzie.restaurant.randomizer.controller.model.RestaurantCreateRequest;
import net.andreinc.mockneat.MockNeat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetRandomRestaurantIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper mapper = new ObjectMapper();

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private QueryUtility queryUtility;

    @BeforeEach
    public void setup() {
        queryUtility = new QueryUtility(mvc);

    }


    // Happy Case
    @Test
    public void getRandomRestaurant_returnsRestaurantItem() throws Exception {
        //GIVEN
        RestaurantCreateRequest restaurantCreateRequest = new RestaurantCreateRequest();
        restaurantCreateRequest.setName(mockNeat.strings().get());
        restaurantCreateRequest.setCategory(mockNeat.strings().get());
//        restaurantCreateRequest.setStoreHours(Collections.singletonList(mockNeat.strings().get()));

        queryUtility.restaurantControllerClient.createRestaurant(restaurantCreateRequest);
        //WHEN
        queryUtility.restaurantControllerClient.getRandomRestaurant()
                //THEN
                .andExpect(status().isOk());

    }
}
