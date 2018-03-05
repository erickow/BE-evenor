package com.ericko.evenor.service;

import com.ericko.evenor.entity.User;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface UserService {
    List<User> getUser();

    User getUser(String id);

    User createUser( @Valid User user);

    User updateUser( @Valid User user);

    void deleteUser(String id);

    List<User> searchByName(String name);
}
