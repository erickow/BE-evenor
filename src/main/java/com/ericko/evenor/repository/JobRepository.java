package com.ericko.evenor.repository;

import com.ericko.evenor.entity.EventComittee;
import com.ericko.evenor.entity.Job;
import com.ericko.evenor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID> {
    List<Job> findAllByComittees_Comittee(User user);
}
