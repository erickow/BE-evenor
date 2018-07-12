package com.ericko.evenor.repository;

import com.ericko.evenor.entity.Event;
import com.ericko.evenor.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VolunteerRepository extends JpaRepository<Volunteer, UUID> {
    List<Volunteer> findAllByEventAndType(Event event , String type);
}
