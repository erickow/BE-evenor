package com.ericko.evenor.repository;

import com.ericko.evenor.entity.Event;
import com.ericko.evenor.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface VoteRepository extends JpaRepository<Vote, UUID> {
    List<Vote> findAllByEvent(Event event);
    List<Vote> findAllByEventAndEndDateAfter(Event event, Date date);
    List<Vote> findAllByEventAndEndDateBefore(Event event, Date date);
}
