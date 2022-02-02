package ru.bootjava.graduating.restaurantsvoting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bootjava.graduating.restaurantsvoting.error.IllegalRequestDataException;
import ru.bootjava.graduating.restaurantsvoting.model.User;
import ru.bootjava.graduating.restaurantsvoting.model.Vote;
import ru.bootjava.graduating.restaurantsvoting.repository.RestaurantRepository;
import ru.bootjava.graduating.restaurantsvoting.repository.UserRepository;
import ru.bootjava.graduating.restaurantsvoting.repository.VoteRepository;
import ru.bootjava.graduating.restaurantsvoting.error.NotFoundException;
import ru.bootjava.graduating.restaurantsvoting.to.VoteTo;
import ru.bootjava.graduating.restaurantsvoting.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.bootjava.graduating.restaurantsvoting.util.VoteToUtil.asVote;
import static ru.bootjava.graduating.restaurantsvoting.util.VoteToUtil.asVoteTo;

@Slf4j
@RequiredArgsConstructor
@Service
public class VoteService {
    public static final LocalTime END_TIME_CHANGE_VOTE = LocalTime.of(11, 0);
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public VoteTo getByUserIdAndLocalDate(int userId, LocalDate localDate) {
        return voteRepository.getByUserIdAndLocalDate(userId, localDate).orElseThrow(() ->
                new NotFoundException("not found vote by date " + localDate + " and user id " + userId));
    }

    public VoteTo getByIdAndUserId(int id, int userId) {
        return voteRepository.getByIdAndUserId(id, userId).orElseThrow(() ->
                new NotFoundException("not found vote by id " + id + " and user id " + userId));
    }

    public List<VoteTo> getAll(int userId) {
        return voteRepository.getAll(userId);
    }

    public int deleteByUserId(int userId, int id) {
        if (LocalTime.now().isBefore(END_TIME_CHANGE_VOTE)) {
            return voteRepository.deleteByUserId(userId, id);
        } else {
            throw new IllegalRequestDataException("You can only delete a voice until 11:00 a.m.");
        }
    }

    @Transactional
    public VoteTo create(VoteTo voteTo) {
        Vote vote = asVote(voteTo);
        User user = SecurityUtil.authUser();
        log.info("create vote: {} by user {}", vote, user);
        vote.setUser(user);
        vote.setRestaurant(restaurantRepository.findById(voteTo.getRestaurantId()).orElseThrow(() ->
                new NotFoundException("not found restaurant by id " + voteTo.getRestaurantId())));
        log.info("convert VoteTo to Vote: {}", vote);
        return asVoteTo(voteRepository.save(vote));
    }

    @Transactional
    public void update(VoteTo voteTo, int id, int userId) {
        if (LocalTime.now().isBefore(END_TIME_CHANGE_VOTE)) {
            LocalDate checkDate = getByIdAndUserId(id, userId).getLocalDate();
            if (checkDate.equals(voteTo.getLocalDate()) && checkDate.equals(LocalDate.now())) {
                Vote vote = new Vote(id);
                vote.setUser(userRepository.findById(userId).orElseThrow(() ->
                        new NotFoundException("not found user by Id " + userId)));
                vote.setRestaurant(restaurantRepository.findById(voteTo.getRestaurantId()).orElseThrow(() ->
                        new NotFoundException("not found restaurant by id " + voteTo.getRestaurantId())));
                log.info("convert VoteTo to Vote: {}", vote);
                voteRepository.save(vote);
            } else {
                throw new IllegalRequestDataException("Voting can only be changed today!");
            }
        } else {
            throw new IllegalRequestDataException("You can only upgrade your voice until 11:00 a.m.");
        }
    }
}
