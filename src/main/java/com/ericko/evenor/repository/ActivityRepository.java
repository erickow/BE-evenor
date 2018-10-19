package com.ericko.evenor.repository;

import com.ericko.evenor.entity.Activity;
import com.ericko.evenor.entity.Event;
import com.ericko.evenor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    List<Activity> findAllByEventOrderByDateDesc(Event event);
    List<Activity> findAllByUserOrderByDateDesc(User user);
}
