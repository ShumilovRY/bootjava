package ru.bootjava.graduating.restaurantsvoting.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.bootjava.graduating.restaurantsvoting.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}
