package Restaurants.randomizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.restaurant.randomizer.IntegrationTest;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetRandomRestaurantIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper mapper = new ObjectMapper();

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private QueryUtility queryUtility;

    @BeforeAll
    public void setup() {
        queryUtility = new QueryUtility(mvc);
    }


    // Happy Case for getRandomRestaurant
    @Test
    public void getRandomRestaurant_validParameters_returnsRestaurantItem() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    public void getRandomRestaurant_invalidParameters_throwsRestaurantNotFound() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    public void getRandomRestaurant_returnsRestaurantItem() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    public void getRandomRestaurant_noRestaurants_throwsRestaurantNotFound() {
        //GIVEN

        //WHEN

        //THEN
    }
}
