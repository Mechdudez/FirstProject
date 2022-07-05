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
    void reviewRestaurant_validRestaurant_reviewIsCreated() {

    }

    @Test
    void reviewRestaurant_invalidReview_throwException() {

    }

    @Test
    void reviewRestaurant_invalidRestaurant_throwException() {

    }

}
