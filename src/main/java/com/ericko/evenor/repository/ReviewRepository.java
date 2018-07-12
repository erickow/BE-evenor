package com.ericko.evenor.repository;

import com.ericko.evenor.entity.Event;
import com.ericko.evenor.entity.Review;
import com.ericko.evenor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findAllByUser(User user);
    Review findByUserAndReviewerAndEvent(User user, User reviewer, Event event);
}
