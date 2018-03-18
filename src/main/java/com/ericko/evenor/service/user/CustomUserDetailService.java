package com.ericko.evenor.service.user;

import com.ericko.evenor.entity.CustomUserDetails;
import com.ericko.evenor.entity.User;
import com.ericko.evenor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("customUserDetailService")
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userName);
        if (null == user) {
            throw new UsernameNotFoundException(
                    "INVALID USER WITH LOGIN: " + userName);
        } else {
            List<String> userRoles = userRepository.findByRole(user.getId());
            return new CustomUserDetails(user, userRoles);
        }
    }
}
