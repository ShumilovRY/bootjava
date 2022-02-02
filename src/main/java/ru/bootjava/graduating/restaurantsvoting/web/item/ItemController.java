package ru.bootjava.graduating.restaurantsvoting.web.item;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bootjava.graduating.restaurantsvoting.model.Item;
import ru.bootjava.graduating.restaurantsvoting.repository.ItemRepository;
import ru.bootjava.graduating.restaurantsvoting.to.ItemTo;

import java.time.LocalDate;
import java.util.List;

import static ru.bootjava.graduating.restaurantsvoting.web.restaurant.RestaurantController.RESTAURANTS_REST_URL;

@Slf4j
@RequiredArgsConstructor
@RestController
@CacheConfig(cacheNames = "item")
@RequestMapping(value = RESTAURANTS_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User Item Controller", description = "Controller for getting the items of the restaurant.")
public class ItemController {
    private final ItemRepository itemRepository;

    @Cacheable
    @Operation(summary = "Get today's items", description = "Get today's restaurant items.")
    @GetMapping(value = AdminItemController.ITEMS_REST_URL)
    public List<Item> getTodayMenu(@PathVariable int restaurantId) {
        log.info("get menu items for restaurant {} on {}", restaurantId, LocalDate.now());
        return itemRepository.getItemByDate(restaurantId, LocalDate.now());
    }

    @Operation(summary = "Get all of today's menu", description = "Get all menus for today for each restaurant.")
    @GetMapping(value = "/menu-items")
    public List<ItemTo> getAllTodayMenu() {
        log.info("get a today's {} menu for each restaurant", LocalDate.now());
        return itemRepository.getAllItemsByLocalDate(LocalDate.now());
    }
}
