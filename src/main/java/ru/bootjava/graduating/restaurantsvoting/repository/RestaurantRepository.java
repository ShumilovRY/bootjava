package ru.bootjava.graduating.restaurantsvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bootjava.graduating.restaurantsvoting.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}
