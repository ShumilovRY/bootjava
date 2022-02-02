package ru.bootjava.graduating.restaurantsvoting.web.item;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.bootjava.graduating.restaurantsvoting.model.Item;
import ru.bootjava.graduating.restaurantsvoting.util.JsonUtil;
import ru.bootjava.graduating.restaurantsvoting.web.restaurant.RestaurantTestData;
import ru.bootjava.graduating.restaurantsvoting.web.AbstractControllerTest;
import ru.bootjava.graduating.restaurantsvoting.web.restaurant.AdminRestaurantController;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.bootjava.graduating.restaurantsvoting.web.TestUtil.userHttpBasic;
import static ru.bootjava.graduating.restaurantsvoting.web.item.ItemTestData.*;
import static ru.bootjava.graduating.restaurantsvoting.web.user.UserTestData.admin;

class AdminItemControllerTest extends AbstractControllerTest {

    private static final String ADMIN_RESTAURANT_REST_URL = AdminRestaurantController.ADMIN_RESTAURANT_REST_URL + '/';
    private static final String ITEMS_REST_URL = "/items/";

    @Test
    void getById() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.RESTAURANT_ID_1 + ITEMS_REST_URL + ITEM_ID_1)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ITEM_MATCHER.contentJson(ITEM_1));
    }

    @Test
    void getByInvalidId() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.RESTAURANT_ID_1 + ITEMS_REST_URL + NOT_FOUND_ITEM)
                .with(userHttpBasic(admin)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getAllForRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.RESTAURANT_ID_1 + ITEMS_REST_URL)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ITEM_MATCHER.contentJson(itemsRestaurant));
    }

    @Test
    void getAllForNotFoundRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.NOT_FOUND_RESTAURANT + ITEMS_REST_URL)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getItemByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.RESTAURANT_ID_1 + ITEMS_REST_URL + "/by")
                .param("date", "2021-12-12")
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ITEM_MATCHER.contentJson(itemList));
    }

    @Test
    void getItemByEmptyDate() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.RESTAURANT_ID_1 + ITEMS_REST_URL + "/by")
                .param("date", "")
                .with(userHttpBasic(admin)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getItemByInvalidDate() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.RESTAURANT_ID_1 + ITEMS_REST_URL + "/by")
                .param("date", "2000-12-12")
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAllItemsByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + ITEMS_REST_URL + "/by")
                .param("date", "2021-12-12")
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ITEM_TO_MATCHER.contentJson(allItemsForDay));
    }

    @Test
    void getAllItemsByEmptyDate() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + ITEMS_REST_URL + "/by")
                .param("date", "")
                .with(userHttpBasic(admin)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getAllItemsByInvalidDate() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + ITEMS_REST_URL + "/by")
                .param("date", "2000-12-12")
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAllItemsBySpaceDate() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + ITEMS_REST_URL + "/by")
                .param("date", " ")
                .with(userHttpBasic(admin)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.RESTAURANT_ID_1 + ITEMS_REST_URL + ITEM_ID_1)
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void deleteNotFoundItemItem() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.RESTAURANT_ID_1 + ITEMS_REST_URL + NOT_FOUND_ITEM)
                .with(userHttpBasic(admin)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void create() throws Exception {
        Item newItem = getNew();
        ResultActions action = perform(
                MockMvcRequestBuilders.post(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.RESTAURANT_ID_1 + ITEMS_REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(admin))
                        .content(JsonUtil.writeValue(newItem)))
                .andExpect(status().isCreated())
                .andDo(print());

        Item created = ITEM_MATCHER.readFromJson(action);
        int newId = created.id();
        newItem.setId(newId);
        ITEM_MATCHER.assertMatch(created, newItem);
        ITEM_MATCHER.assertMatch(itemRepository.getById(newId).get(), newItem);
    }

    @Test
    void createNotFoundRestaurant() throws Exception {
        Item newItem = getNew();
        perform(MockMvcRequestBuilders.post(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.NOT_FOUND_RESTAURANT + ITEMS_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(newItem)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void createNewItemWithId() throws Exception {
        Item newItem = getNew();
        newItem.setId(ITEM_ID_1);
        perform(MockMvcRequestBuilders.post(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.RESTAURANT_ID_1 + ITEMS_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(newItem)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void update() throws Exception {
        Item updatedItem = itemRepository.getById(ITEM_ID_1).get();
        updatedItem.setPrice(999);
        perform(MockMvcRequestBuilders.put(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.RESTAURANT_ID_1 + ITEMS_REST_URL + ITEM_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedItem))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void updateNotFound() throws Exception {
        Item updatedItem = itemRepository.getById(ITEM_ID_1).get();
        updatedItem.setPrice(999);
        perform(MockMvcRequestBuilders.put(ADMIN_RESTAURANT_REST_URL + RestaurantTestData.RESTAURANT_ID_1 + ITEMS_REST_URL + NOT_FOUND_ITEM)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedItem))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
