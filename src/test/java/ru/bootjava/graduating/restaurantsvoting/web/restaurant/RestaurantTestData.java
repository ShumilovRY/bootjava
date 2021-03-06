package ru.bootjava.graduating.restaurantsvoting.web.restaurant;

import ru.bootjava.graduating.restaurantsvoting.model.Restaurant;
import ru.bootjava.graduating.restaurantsvoting.web.MatcherFactory;
import ru.bootjava.graduating.restaurantsvoting.to.RestaurantTo;

import java.util.List;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menu", "votes");

    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER =
            MatcherFactory.usingEqualsComparator(RestaurantTo.class);

    public static final int NOT_FOUND_RESTAURANT = 0;
    public static final int RESTAURANT_ID_1 = 1;
    public static final int RESTAURANT_ID_2 = 2;
    public static final int RESTAURANT_ID_3 = 3;

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_ID_1, "Mak");
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_ID_2, "BK");
    public static final Restaurant RESTAURANT_3 = new Restaurant(RESTAURANT_ID_3, "KFC");

    public static final List<Restaurant> allRestaurants = List.of(RESTAURANT_2, RESTAURANT_3, RESTAURANT_1);

    public static final RestaurantTo restaurantTo1 = new RestaurantTo(RESTAURANT_ID_1, "Mak", 1L);
    public static final RestaurantTo restaurantTo2 = new RestaurantTo(RESTAURANT_ID_2, "BK", 2L);

    public static final RestaurantTo restaurantToDay1 = new RestaurantTo(RESTAURANT_ID_1, "Mak", 1L);
    public static final RestaurantTo restaurantToDay2 = new RestaurantTo(RESTAURANT_ID_2, "BK", 1L);
    public static final List<RestaurantTo> restaurantTos = List.of(restaurantToDay2, restaurantToDay1);

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant");
    }
}
