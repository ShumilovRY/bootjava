package ru.bootjava.graduating.restaurantsvoting.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.bootjava.graduating.restaurantsvoting.model.Lunch;
import ru.bootjava.graduating.restaurantsvoting.repository.LunchRepository;

import java.util.List;

@RestController
@RequestMapping("/api/lunches")
@AllArgsConstructor
@Slf4j
public class LunchController {

    private final LunchRepository lunchRepository;

    @DeleteMapping(value = "/{id}")//todo: fix
    public void delete(@PathVariable int id) {
        log.debug("before, lunches count : {}", lunchRepository.count());
        lunchRepository.deleteById(id);
        log.debug("after, lunches count : {}", lunchRepository.count());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Lunch get(@PathVariable int id) {
        log.info("get lunch with id {}", id);
        return lunchRepository.findById(id).get();
    }

    @GetMapping
    public List<Lunch> fetAll() {
        log.info("get all lunches");
        return lunchRepository.findAll();
    }
}