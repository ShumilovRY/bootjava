package ru.bootjava.graduating.restaurantsvoting.web.item;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.bootjava.graduating.restaurantsvoting.error.IllegalRequestDataException;
import ru.bootjava.graduating.restaurantsvoting.model.Item;
import ru.bootjava.graduating.restaurantsvoting.repository.ItemRepository;
import ru.bootjava.graduating.restaurantsvoting.repository.RestaurantRepository;
import ru.bootjava.graduating.restaurantsvoting.View;
import ru.bootjava.graduating.restaurantsvoting.error.NotFoundException;
import ru.bootjava.graduating.restaurantsvoting.to.ItemTo;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.bootjava.graduating.restaurantsvoting.util.validation.ValidationUtil.*;
import static ru.bootjava.graduating.restaurantsvoting.web.restaurant.AdminRestaurantController.ADMIN_RESTAURANT_REST_URL;

@Slf4j
@CacheConfig(cacheNames = "item")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ADMIN_RESTAURANT_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Admin Item Controller", description = "Controller for editing items by admin.")
public class AdminItemController {
    public static final String ITEMS_REST_URL = "/{restaurantId}/items";

        private final ItemRepository itemRepository;
        private final RestaurantRepository restaurantRepository;

        @Operation(summary = "Get a  item", description = "Get a restaurant item.")
        @GetMapping(value = ITEMS_REST_URL + "/{id}")
        public Item getById(@PathVariable int id, @PathVariable int restaurantId) {
            log.info("get item {} for restaurant {}", id, restaurantId);
            Item item = itemRepository.getById(id)
                    .orElseThrow(() -> new NotFoundException("not found item " + id));
            Integer realRestaurantId = item.getRestaurant().getId();
            if (realRestaurantId != null && realRestaurantId == restaurantId) {
                return item;
            } else {
                throw new IllegalRequestDataException(
                        "This restaurant: " + restaurantId + " does not own this item: " + id);
            }
        }

        @Operation(summary = "Get all restaurant", description = "Get all restaurant items for all time.")
        @GetMapping(value = ITEMS_REST_URL)
        public List<Item> getAll(@PathVariable int restaurantId) {
            log.info("get all items for restaurant {}", restaurantId);
            return itemRepository.getAll(restaurantId);
        }

        @Cacheable
        @Operation(summary = "Get item", description = "Get a restaurant item on a given day.")
        @GetMapping(value = ITEMS_REST_URL + "/by")
        public List<Item> getItemByDate(@PathVariable int restaurantId,
                                        @NotNull @RequestParam(value = "date") LocalDate localDate) {
            log.info("get items for restaurant {} on {}", restaurantId, localDate);
            return itemRepository.getItemByDate(restaurantId, localDate);
        }

        @Operation(summary = "Get all", description = "Get all items for all restaurants on a given day.")
        @GetMapping(value = "/items/by")
        public List<ItemTo> getAllItemsByLocalDate(@NotNull @RequestParam(value = "date") LocalDate localDate) {
            log.info("get a item for each restaurant by date {}", localDate);
            return itemRepository.getAllItemsByLocalDate(localDate);
        }

        @CacheEvict(allEntries = true)
        @Operation(summary = "Creating a Item", description = "Creating a Item for a restaurant.")
        @PostMapping(value = ITEMS_REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Item> create(@Validated(View.Web.class) @RequestBody Item item,
                                           @PathVariable int restaurantId) {
            Assert.notNull(item, "Item must not be null!");
            checkNew(item);
            log.info("create item {} for restaurant {}", item, restaurantId);
            item.setRestaurant(restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new NotFoundException("not found restaurant " + restaurantId)));
            Item created = itemRepository.save(item);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(ADMIN_RESTAURANT_REST_URL + ITEMS_REST_URL + "/{id}")
                    .buildAndExpand(restaurantId, created.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(created);
        }

        @CacheEvict(allEntries = true)
        @Operation(summary = "Item update", description = "Renovation of the restaurant items.")
        @PutMapping(value = ITEMS_REST_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void update(@Validated(View.Web.class) @RequestBody Item item, @PathVariable int id,
                           @PathVariable int restaurantId) {
            Assert.notNull(item, "item must not be null!");
            log.info("update item {}, Item id {} for restaurant {}", item, id, restaurantId);
            assureIdConsistent(item, id);

            Item checkItem = getById(id, restaurantId);

            item.setRestaurant(restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new NotFoundException("not found restaurant " + restaurantId)));
            if (!item.equals(checkItem)) {
                checkNotFoundWithId(itemRepository.save(item), id);
            }
        }

        @CacheEvict(allEntries = true)
        @Operation(summary = "Deleting a item", description = "Deleting a item in a specific restaurant.")
        @DeleteMapping(value = ITEMS_REST_URL + "/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void delete(@PathVariable int restaurantId, @PathVariable int id) {
            log.info("delete item {} for restaurant {}", id, restaurantId);
            checkNotFoundWithId(itemRepository.delete(id, restaurantId) != 0, id);
        }
}
