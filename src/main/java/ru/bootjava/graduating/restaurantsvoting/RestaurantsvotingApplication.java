package ru.bootjava.graduating.restaurantsvoting;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.bootjava.graduating.restaurantsvoting.repository.RestaurantRepository;

@SpringBootApplication
@AllArgsConstructor
public class RestaurantsvotingApplication implements ApplicationRunner {

    private final RestaurantRepository restaurantRepository;

    public static void main(String[] args) {
        SpringApplication.run(RestaurantsvotingApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception { }
}