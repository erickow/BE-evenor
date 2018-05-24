package com.ericko.evenor.repository;

import com.ericko.evenor.entity.EventComittee;
import com.ericko.evenor.entity.User;
import com.ericko.evenor.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventComitteeRepository extends JpaRepository<EventComittee,UUID>{
    List<EventComittee> findAllByComitteeAndEvent_EndDateAfter(User user, Date date);
    List<EventComittee> findAllByComitteeAndEvent_EndDateBefore(User user, Date date);
    List<EventComittee> findAllByEvent_Id(UUID id);
    EventComittee findByComitteeAndEvent(User user, Event event);

    List<EventComittee> findAllByComittee_NameContaining(String name);

    Integer countEventComitteeByEvent(Event event);
}
