package ru.bootjava.graduating.restaurantsvoting.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.bootjava.graduating.restaurantsvoting.web.AbstractControllerTest;
import ru.bootjava.graduating.restaurantsvoting.to.RestaurantTo;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.bootjava.graduating.restaurantsvoting.web.TestUtil.userHttpBasic;
import static ru.bootjava.graduating.restaurantsvoting.web.user.UserTestData.user;

class RestaurantControllerTest extends AbstractControllerTest {

    private static final String RESTAURANTS_REST_URL = RestaurantController.RESTAURANTS_REST_URL + '/';

    @Test
    void getById() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + RestaurantTestData.RESTAURANT_ID_2)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_TO_MATCHER.contentJson(new RestaurantTo(RestaurantTestData.RESTAURANT_ID_2, "Burger Empire", 2L)));
    }

    @Test
    void getNotFoundById() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + RestaurantTestData.NOT_FOUND_RESTAURANT)
                .with(userHttpBasic(user)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getByIdAndLocalDate() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + RestaurantTestData.RESTAURANT_ID_2 + "/by")
                .param("date", "2021-12-12")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_TO_MATCHER.contentJson(new RestaurantTo(RestaurantTestData.RESTAURANT_ID_2, "Burger Empire", 1L)));
    }

    @Test
    void getNotFoundByIdAndLocalDate() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + RestaurantTestData.RESTAURANT_ID_2 + "/by")
                .param("date", "2000-12-12")
                .with(userHttpBasic(user)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + "/by")
                .param("date", "2021-12-12")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_TO_MATCHER.contentJson(RestaurantTestData.restaurantTos));
    }

    @Test
    void getAllByInvalidDate() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + "/by")
                .param("date", "2021")
                .with(userHttpBasic(user)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getAllByNotFoundDate() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + "/by")
                .param("date", "1999-12-12")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
