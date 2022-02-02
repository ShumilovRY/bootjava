package ru.bootjava.graduating.restaurantsvoting.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.bootjava.graduating.restaurantsvoting.model.Restaurant;
import ru.bootjava.graduating.restaurantsvoting.util.JsonUtil;
import ru.bootjava.graduating.restaurantsvoting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.bootjava.graduating.restaurantsvoting.web.TestUtil.userHttpBasic;
import static ru.bootjava.graduating.restaurantsvoting.web.user.UserTestData.admin;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String ADMIN_RESTAURANT_REST_URL = AdminRestaurantController.ADMIN_RESTAURANT_REST_URL + '/';

    @Test
    void getById() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.RESTAURANT_ID_2)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_MATCHER.contentJson(RestaurantTestData.RESTAURANT_2));
    }

    @Test
    void getNotFoundById() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.NOT_FOUND_RESTAURANT)
                .with(userHttpBasic(admin)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_MATCHER.contentJson(RestaurantTestData.allRestaurants));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.RESTAURANT_ID_1)
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.NOT_FOUND_RESTAURANT)
                .with(userHttpBasic(admin)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(ADMIN_RESTAURANT_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated())
                .andDo(print());

        Restaurant created = RestaurantTestData.RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
    }

    @Test
    void createWithId() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        newRestaurant.setId(RestaurantTestData.RESTAURANT_ID_1);
        perform(MockMvcRequestBuilders.post(ADMIN_RESTAURANT_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void update() throws Exception {
        Restaurant updatedRestaurant = new Restaurant(RestaurantTestData.RESTAURANT_ID_1, "Updated Restaurant");
        perform(MockMvcRequestBuilders.put(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.RESTAURANT_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedRestaurant))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void updateWithNonExistingId() throws Exception {
        Restaurant updatedRestaurant = new Restaurant(RestaurantTestData.NOT_FOUND_RESTAURANT, "Updated Restaurant");
        perform(MockMvcRequestBuilders.put(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.RESTAURANT_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedRestaurant))
                .with(userHttpBasic(admin)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }
}