package com.ericko.evenor.repository;

import com.ericko.evenor.entity.Answer;
import com.ericko.evenor.entity.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
@Transactional
public interface AnswerRepository extends JpaRepository<Answer, UUID> {
}
