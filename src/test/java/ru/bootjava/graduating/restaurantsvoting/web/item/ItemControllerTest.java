package ru.bootjava.graduating.restaurantsvoting.web.item;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.bootjava.graduating.restaurantsvoting.web.restaurant.RestaurantTestData;
import ru.bootjava.graduating.restaurantsvoting.web.AbstractControllerTest;
import ru.bootjava.graduating.restaurantsvoting.web.restaurant.RestaurantController;

import static ru.bootjava.graduating.restaurantsvoting.web.TestUtil.userHttpBasic;
import static ru.bootjava.graduating.restaurantsvoting.web.item.ItemTestData.*;

public class ItemControllerTest extends AbstractControllerTest {
    private static final String RESTAURANTS_REST_URL = RestaurantController.RESTAURANTS_REST_URL + '/';
    private static final String ITEMS_REST_URL = "/items/";

    @Test
    void getItemForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + RestaurantTestData.RESTAURANT_ID_3 + ITEMS_REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ITEM_MATCHER.contentJson(todayItem));
    }

    @Test
    void getItemForTodayWithoutDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + RestaurantTestData.RESTAURANT_ID_1 + ITEMS_REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAllItemsForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + ITEMS_REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ITEM_TO_MATCHER.contentJson(allItemsForToday));
    }
}