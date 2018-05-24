package com.ericko.evenor.service.vote;

import com.ericko.evenor.entity.*;
import com.ericko.evenor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventComitteeRepository eventComitteeRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestRepository questRepository;

    @Override
    public List<Vote> getVoteByEvent(UUID id) {

        return voteRepository.findAllByEvent(eventRepository.findOne(id));
    }

    @Override
    public Vote getVote(UUID id) {
        return voteRepository.findOne(id);
    }

    @Override
    public List<Vote> getUpcomingVote(UUID id) {
        Event event = eventRepository.findOne(id);
        return voteRepository.findAllByEventAndEndDateAfter(event, new Date());
    }

    @Override
    public List<Vote> getHistoryVote(UUID id) {
        Event event = eventRepository.findOne(id);
        return voteRepository.findAllByEventAndEndDateBefore(event, new Date());
    }

    @Override
    public Vote createVote(UUID eventId, UUID userId, Vote vote) {
        Event event = eventRepository.findOne(eventId);
        User user = userRepository.findOne(userId);
        EventComittee comittee = eventComitteeRepository.findByComitteeAndEvent(user, event);
        vote.setEvent(event);
        vote.setTotalVoter(eventComitteeRepository.countEventComitteeByEvent(event));
        List<Answer> answers = new ArrayList<>();
        vote.getOptions().stream().forEach(ans -> { answers.add(answerRepository.save(ans));});
        vote.setOptions(answers);
        Quest quest = questRepository.findByCode("#ADD_VOTE");
        comittee.setScore(comittee.getScore() + quest.getScore());
        user.setExperience(user.getExperience() + quest.getScore());
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
        userRepository.findOne(id);

        return voterRepository.save(voter);
    }

    @Override
    public Voter updateVoter(Voter voter) {
        return voterRepository.save(voter);
    }
}
