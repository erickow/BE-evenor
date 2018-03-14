package com.ericko.evenor.service.user;

import com.ericko.evenor.entity.User;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Validated
public interface UserService {
    List<User> getUser();

    User getUser(UUID id);

    User createUser( @Valid User user);

    User updateUser( @Valid User user);

    void deleteUser(UUID id);

    List<User> searchByName(String name);

}
