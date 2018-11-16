package com.ericko.evenor.repository;

import com.ericko.evenor.entity.Answer;
import com.ericko.evenor.entity.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, UUID> {
    Answer findByVotersAndId(Voter voter, UUID id);
    Answer findByVoters(Voter voter);
}
