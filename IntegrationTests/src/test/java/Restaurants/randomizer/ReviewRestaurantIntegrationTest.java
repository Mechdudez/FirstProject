package Restaurants.randomizer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.restaurant.randomizer.IntegrationTest;
import com.kenzie.restaurant.randomizer.controller.model.RestaurantCreateRequest;
import com.kenzie.restaurant.randomizer.controller.model.ReviewCreateRequest;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReviewRestaurantIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper mapper = new ObjectMapper();

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private QueryUtility queryUtility;

    @BeforeAll
    public void setup() {
        queryUtility = new QueryUtility(mvc);
    }

    // Happy Case for reviewRestaurant
    @Test
    public void reviewRestaurant_validRestaurant_reviewIsCreated() throws Exception {
        // GIVEN
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest();
        reviewCreateRequest.setRestaurantId(mockNeat.strings().get());
        reviewCreateRequest.setUserId(mockNeat.strings().get());
        reviewCreateRequest.setRestaurantName(mockNeat.strings().get());
        reviewCreateRequest.setPrice(mockNeat.doubles().get());
        reviewCreateRequest.setRating(mockNeat.ints().get());
        reviewCreateRequest.setDescription(mockNeat.strings().get());

        // WHEN
        queryUtility.reviewControllerClient.createReview(reviewCreateRequest)
                //THEN
                .andExpect(status().isOk());
    }

    @Test
    public void reviewRestaurant_EmptyReview_throwException() throws Exception {
        //GIVEN
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest();
        reviewCreateRequest.setRestaurantId("");
        reviewCreateRequest.setUserId("");
        reviewCreateRequest.setRestaurantName("");
        reviewCreateRequest.setPrice(0.0);
        reviewCreateRequest.setRating(0);
        reviewCreateRequest.setDescription("");

        RestaurantCreateRequest restaurantCreateRequest = new RestaurantCreateRequest();
        restaurantCreateRequest.setUserId(reviewCreateRequest.getUserId());
        restaurantCreateRequest.setUserId(reviewCreateRequest.getRestaurantId());
        restaurantCreateRequest.setUserId(reviewCreateRequest.getRestaurantName());


        //WHEN/THEN
        Assertions.assertThrows(NestedServletException.class, () -> {
            queryUtility.reviewControllerClient.createReview(reviewCreateRequest);
        });
    }

    @Test
    public void reviewRestaurant_NullRestaurant_ReturnBadRequest() throws Exception {
        //GIVEN
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest();
        reviewCreateRequest.setRestaurantId(mockNeat.strings().get());
        reviewCreateRequest.setUserId(mockNeat.strings().get());
        reviewCreateRequest.setRestaurantName(mockNeat.strings().get());
        reviewCreateRequest.setPrice(mockNeat.doubles().get());
        reviewCreateRequest.setRating(mockNeat.ints().get());
        reviewCreateRequest.setDescription(mockNeat.strings().get());

        //WHEN //THEN
        queryUtility.restaurantControllerClient.createRestaurant(null);
        queryUtility.reviewControllerClient.createReview(reviewCreateRequest)
                .andExpect(status().isBadRequest());
    }

}
