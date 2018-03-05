package com.ericko.evenor.service;

import com.ericko.evenor.entity.User;
import com.ericko.evenor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUser() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getUser(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.delete(id);
    }

    @Override
    public List<User> searchByName(String name) {
        return userRepository.findAllByNameContains(name);
    }
}
