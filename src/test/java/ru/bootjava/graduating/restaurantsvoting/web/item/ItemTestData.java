package ru.bootjava.graduating.restaurantsvoting.web.item;

import ru.bootjava.graduating.restaurantsvoting.model.Item;
import ru.bootjava.graduating.restaurantsvoting.web.vote.VoteTestData;
import ru.bootjava.graduating.restaurantsvoting.web.MatcherFactory;
import ru.bootjava.graduating.restaurantsvoting.to.ItemTo;

import java.time.LocalDate;
import java.util.List;

public class ItemTestData {
    public static final MatcherFactory.Matcher<Item> ITEM_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Item.class, "restaurant");

    public static final MatcherFactory.Matcher<ItemTo> ITEM_TO_MATCHER =
            MatcherFactory.usingEqualsComparator(ItemTo.class);

    public static final int NOT_FOUND_ITEM = 0;
    public static final int ITEM_ID_1 = 1;
    public static final Item ITEM_1 = new Item(ITEM_ID_1, "Mak Burger1", 111, VoteTestData.date1);
    public static final Item ITEM_2 = new Item(2, "Mak Burger2", 112, VoteTestData.date1);
    public static final Item ITEM_3 = new Item(3, "Mak Burger3", 113, VoteTestData.date1);
    public static final Item ITEM_4 = new Item(4, "BK Burger1", 211, VoteTestData.date1);
    public static final Item ITEM_5 = new Item(5, "BK Burger2", 212, VoteTestData.date1);
    public static final Item ITEM_6 = new Item(6, "BK Burger3", 213, VoteTestData.date1);
    public static final Item ITEM_7 = new Item(7, "KFC Burger1", 311, VoteTestData.date1);
    public static final Item ITEM_8 = new Item(8, "KFC Burger2", 312, VoteTestData.date1);
    public static final Item ITEM_9 = new Item(9, "KFC Burger3", 313, VoteTestData.date1);
    public static final Item ITEM_10 = new Item(10, "Carbanara", 411, LocalDate.now());
    public static final Item ITEM_11 = new Item(11, "Sushi", 412, LocalDate.now());

    public static final ItemTo ITEM_TO_1 = new ItemTo(ITEM_ID_1, "Mak Burger1", 111, VoteTestData.date1, 1);
    public static final ItemTo ITEM_TO_2 = new ItemTo(2, "Mak Burger2", 112, VoteTestData.date1, 1);
    public static final ItemTo ITEM_TO_3 = new ItemTo(3, "Mak Burger3", 113, VoteTestData.date1, 1);
    public static final ItemTo ITEM_TO_4 = new ItemTo(4, "BK Burger1", 211, VoteTestData.date1,2);
    public static final ItemTo ITEM_TO_5 = new ItemTo(5, "BK Burger2", 212, VoteTestData.date1,2);
    public static final ItemTo ITEM_TO_6 = new ItemTo(6, "BK Burger3", 213, VoteTestData.date1,2);
    public static final ItemTo ITEM_TO_7 = new ItemTo(7, "KFC Burger1", 311, VoteTestData.date1,3);
    public static final ItemTo ITEM_TO_8 = new ItemTo(8, "KFC Burger2", 312, VoteTestData.date1,3);
    public static final ItemTo ITEM_TO_9 = new ItemTo(9, "KFC Burger3", 313, VoteTestData.date1,3);
    public static final ItemTo ITEM_TO_10 = new ItemTo(10, "Carbanara", 411, LocalDate.now(), 1);
    public static final ItemTo ITEM_TO_11 = new ItemTo(11, "Sushi", 412, LocalDate.now(), 2);

    public static final List<Item> itemList = List.of(ITEM_3, ITEM_1, ITEM_2);
    public static final List<Item> todayItem = List.of(ITEM_10, ITEM_11);

    public static final List<Item> itemsRestaurant = List.of(ITEM_10, ITEM_3, ITEM_1, ITEM_2);

    public static final List<ItemTo> allItemsForDay = List.of(ITEM_TO_3, ITEM_TO_1, ITEM_TO_2,
            ITEM_TO_5, ITEM_TO_4, ITEM_TO_6, ITEM_TO_9, ITEM_TO_8, ITEM_TO_7);

    public static final List<ItemTo> allItemsForToday = List.of(ITEM_TO_10, ITEM_TO_11);

    protected static Item getNew() {
        return new Item(null, "newItem", 9999, LocalDate.now());
    }
}