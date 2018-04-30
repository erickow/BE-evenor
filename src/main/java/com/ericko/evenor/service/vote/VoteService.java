package com.ericko.evenor.service.vote;

import com.ericko.evenor.entity.Vote;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface VoteService {

    List<Vote> getVoteByEvent(UUID id);

    Vote getVote(UUID id);

    List<Vote> getUpcomingVote(UUID id);

    List<Vote> getHistoryVote(UUID id);

    Vote createVote(Vote vote);

    Vote updateVote(Vote vote);

    void deleteVote(UUID id);
}
