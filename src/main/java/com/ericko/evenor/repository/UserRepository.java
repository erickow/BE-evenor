package com.ericko.evenor.repository;

import com.ericko.evenor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User,UUID> {

    List<User> findAllByNameContains(String name);

    User findByEmail(String email);

//    @Query("select r.name " + "from User u, User.role r where  u.id=?1 ")
    @Transactional
    List<String> findByRole(UUID id);

}
