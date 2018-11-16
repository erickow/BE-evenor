package com.ericko.evenor.repository;

import com.ericko.evenor.entity.EventComittee;
import com.ericko.evenor.entity.EventParticipant;
import com.ericko.evenor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, UUID> {

    List<EventParticipant> findAllByParticipantAndEvent_EndDateAfter(User user, Date date);
    List<EventParticipant> findAllByParticipantAndEvent_EndDateBefore(User user, Date date);
    List<EventParticipant> findAllByEvent_Id(UUID id);
}
