package com.ericko.evenor.repository;

import com.ericko.evenor.entity.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuestRepository extends JpaRepository<Quest,UUID> {
    Quest findByCode(String code);
}
