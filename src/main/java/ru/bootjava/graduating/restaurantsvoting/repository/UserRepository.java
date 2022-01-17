package ru.bootjava.graduating.restaurantsvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.bootjava.graduating.restaurantsvoting.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.email=LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(String email);
}
