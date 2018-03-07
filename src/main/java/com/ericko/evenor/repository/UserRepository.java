package com.ericko.evenor.repository;

import com.ericko.evenor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    List<User> findAllByNameContains(String name);

    @Modifying
    @Query("delete from User u where u.id = ?1")
    void delete(UUID uid);
}
