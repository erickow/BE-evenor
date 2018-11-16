package com.ericko.evenor.service.vote;

import com.ericko.evenor.entity.*;
import com.ericko.evenor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

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
        vote.getAnswers().stream().forEach(ans -> { answers.add(answerRepository.save(ans));});
        vote.setAnswers(answers);
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
    public Answer createVoter(UUID voteId, UUID answerId, UUID eventComitteeId) {
        Vote vote = voteRepository.findOne(voteId);
        EventComittee comittee = eventComitteeRepository.findOne(eventComitteeId);
        List<Answer> answer = vote.getAnswers());
        for (Answer theAnswer : answer) {
            List<Voter> voters = theAnswer.getVoters();
            for (Voter theVoter : voters) {
                if (theVoter.getEventComittee().equals(comittee)){
                    voters.remove(theVoter);
                }
            }
            Answer temp = theAnswer;
            temp.setVoter = voters;
            answer.remove(theAnswer);
            answer.add(temp);
        }
        return answerRepository.save(answer);
    }

    @Override
    public Voter updateVoter(Voter voter) {
        return voterRepository.save(voter);
    }
}
