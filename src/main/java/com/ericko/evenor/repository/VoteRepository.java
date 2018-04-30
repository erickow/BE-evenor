package com.ericko.evenor.repository;

import com.ericko.evenor.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {
    List<Vote> findAllByEvent_Id(UUID id);
    List<Vote> findAllByEvent_IdAndEvent_EndDateAfter(UUID id, Date date);
    List<Vote> findAllByEvent_IdAndEvent_EndDateBefore(UUID id, Date date);
}
