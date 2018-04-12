package com.ericko.evenor.repository;

import com.ericko.evenor.entity.Event;
import com.ericko.evenor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event,UUID>{

    List<Event> findAllByComitteeContainsAndEndDateAfter(User user, Date date);

    List<Event> findAllByComitteeContainsAndEndDateBeforeOrderByEndDateDesc(User user, Date date);

}
