package ru.bootjava.graduating.restaurantsvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.bootjava.graduating.restaurantsvoting.model.Restaurant;
import ru.bootjava.graduating.restaurantsvoting.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    List<Restaurant> findAllByOrderByTitleAsc();

    @Query("SELECT new ru.bootjava.graduating.restaurantsvoting.to.RestaurantTo(r.id, r.title, count(v.id)) " +
            "FROM Restaurant r LEFT OUTER JOIN Vote v ON r.id = v.restaurant.id " +
            "WHERE v.localDate=:localDate " +
            "GROUP BY r.id, r.title " +
            "ORDER BY count(v.id) DESC, r.title ASC")
    List<RestaurantTo> getAllByDate(LocalDate localDate);

    @Query("SELECT new ru.bootjava.graduating.restaurantsvoting.to.RestaurantTo(r.id, r.title, count(v.id)) " +
            "FROM Restaurant r LEFT OUTER JOIN Vote v ON r.id = v.restaurant.id " +
            "WHERE r.id=:id AND v.localDate=:localDate " +
            "GROUP BY r.title")
    Optional<RestaurantTo> getByIdAndLocalDate(int id, LocalDate localDate);

    @Query("SELECT new ru.bootjava.graduating.restaurantsvoting.to.RestaurantTo(r.id, r.title, count(v.id)) " +
            "FROM Restaurant r LEFT OUTER JOIN Vote v ON r.id = v.restaurant.id " +
            "WHERE r.id=:id " +
            "GROUP BY r.title")
    Optional<RestaurantTo> getById(int id);
}
