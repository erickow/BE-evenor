package com.ericko.evenor.repository;

import com.ericko.evenor.entity.JobComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JobCommentRepository extends JpaRepository<JobComment, UUID> {
}
