package ru.bootjava.graduating.restaurantsvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bootjava.graduating.restaurantsvoting.model.Lunch;

public interface LunchRepository extends JpaRepository<Lunch, Integer> {
}
