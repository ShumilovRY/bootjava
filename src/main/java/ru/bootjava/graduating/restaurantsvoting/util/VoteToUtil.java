package ru.bootjava.graduating.restaurantsvoting.util;

import lombok.experimental.UtilityClass;
import ru.bootjava.graduating.restaurantsvoting.model.Vote;
import ru.bootjava.graduating.restaurantsvoting.to.VoteTo;

@UtilityClass
public class VoteToUtil {

    public static Vote asVote(VoteTo voteTo) {
        return new Vote(voteTo.getId());
    }

    public static VoteTo asVoteTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getLocalDate(), vote.getLocalTime(), vote.getRestaurant().id());
    }
}
