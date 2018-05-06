package com.ericko.evenor.service.vote;

import com.ericko.evenor.entity.Vote;
import com.ericko.evenor.entity.Voter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface VoteService {

    List<Vote> getVoteByEvent(UUID id);

    Vote getVote(UUID id);

    List<Vote> getUpcomingVote(UUID id);

    List<Vote> getHistoryVote(UUID id);

    Vote createVote(UUID id, Vote vote);

    Vote updateVote(Vote vote);

    void deleteVote(UUID id);

    Voter createVoter(UUID id, Voter voter);

    Voter updateVoter(Voter voter);
}
