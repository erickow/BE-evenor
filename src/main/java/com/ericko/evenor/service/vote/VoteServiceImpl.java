package com.ericko.evenor.service.vote;

import com.ericko.evenor.entity.Vote;
import com.ericko.evenor.entity.Voter;
import com.ericko.evenor.repository.EventRepository;
import com.ericko.evenor.repository.VoteRepository;
import com.ericko.evenor.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Vote> getVoteByEvent(UUID id) {
        return voteRepository.findAllByEvent_Id(id);
    }

    @Override
    public Vote getVote(UUID id) {
        return voteRepository.findOne(id);
    }

    @Override
    public List<Vote> getUpcomingVote(UUID id) {
        return voteRepository.findAllByEvent_IdAndEvent_EndDateAfter(id, new Date());
    }

    @Override
    public List<Vote> getHistoryVote(UUID id) {
        return voteRepository.findAllByEvent_IdAndEvent_EndDateBefore(id, new Date());
    }

    @Override
    public Vote createVote(UUID id, Vote vote) {
        vote.setEvent(eventRepository.findOne(id));
        //create option
        return voteRepository.save(vote);
    }

    @Override
    public Vote updateVote(Vote vote) {
        return voteRepository.save(vote);
    }

    @Override
    public void deleteVote(UUID id) {
        voteRepository.delete(id);
    }

    @Override
    public Voter createVoter(UUID id, Voter voter) {
        //search option
        return voterRepository.save(voter);
    }

    @Override
    public Voter updateVoter(Voter voter) {
        return voterRepository.save(voter);
    }
}
