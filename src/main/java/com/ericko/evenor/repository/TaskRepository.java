package com.ericko.evenor.repository;

import com.ericko.evenor.entity.Event;
import com.ericko.evenor.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findAllByEvent(Event event);
}
