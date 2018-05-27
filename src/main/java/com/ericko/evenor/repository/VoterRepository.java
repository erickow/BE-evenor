package com.ericko.evenor.repository;

import com.ericko.evenor.entity.EventComittee;
import com.ericko.evenor.entity.User;
import com.ericko.evenor.entity.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoterRepository extends JpaRepository<Voter, UUID> {
    Voter findByEventComittee(EventComittee eventComittee);
}
