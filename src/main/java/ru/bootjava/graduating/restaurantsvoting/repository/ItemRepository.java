package ru.bootjava.graduating.restaurantsvoting.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.bootjava.graduating.restaurantsvoting.model.Item;
import ru.bootjava.graduating.restaurantsvoting.to.ItemTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends BaseRepository<Item> {

    @Query("SELECT d FROM Item d WHERE d.id=:id")
    Optional<Item> getById(int id);

    @Query("SELECT d FROM Item d " +
            "WHERE d.restaurant.id=:restaurantId AND d.localDate = :localDate " +
            "ORDER BY d.title ASC, d.price ASC")
    List<Item> getItemByDate(int restaurantId, LocalDate localDate);

    @Query("SELECT d FROM Item d " +
            "WHERE d.restaurant.id=:restaurantId " +
            "ORDER BY d.localDate DESC, d.title ASC ")
    List<Item> getAll(int restaurantId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Item d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    int delete(int id, int restaurantId);

    @Query("SELECT new ru.bootjava.graduating.restaurantsvoting.to.ItemTo(d.id, d.title, d.price, d.localDate, d.restaurant.id) " +
            "FROM Item d " +
            "WHERE d.localDate=:localDate " +
            "GROUP BY d.title " +
            "ORDER BY d.restaurant.id ASC, d.title ASC")
    List<ItemTo> getAllItemsByLocalDate(LocalDate localDate);
}
