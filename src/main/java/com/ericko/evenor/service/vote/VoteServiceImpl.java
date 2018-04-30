package com.ericko.evenor.service.vote;

import com.ericko.evenor.entity.Vote;
import com.ericko.evenor.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

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
    public Vote createVote(Vote vote) {
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
}
