package ru.bootjava.graduating.restaurantsvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bootjava.graduating.restaurantsvoting.model.Item;

public interface ItemRepository  extends JpaRepository<Item, Integer> {
}
