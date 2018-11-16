package com.ericko.evenor;

import com.ericko.evenor.entity.CustomUserDetails;
import com.ericko.evenor.entity.Event;
import com.ericko.evenor.entity.Role;
import com.ericko.evenor.entity.User;
import com.ericko.evenor.repository.EventRepository;
import com.ericko.evenor.repository.RoleRepository;
import com.ericko.evenor.repository.UserRepository;
import com.ericko.evenor.service.event.EventService;
import com.ericko.evenor.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
@EnableAuthorizationServer
public class EvenorApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(EvenorApplication.class, args);
	}

	/**
	 * Password grants are switched on by injecting an AuthenticationManager.
	 * Here, we setup the builder so that the userDetailsService is the one we coded.
	 * @param builder
	 * @param userRepository
	 * @throws Exception
	 */
	@Autowired
	public void authenticationManager(
            AuthenticationManagerBuilder builder,
            UserRepository userRepository,
            UserService userService,
			EventRepository eventRepository,
            RoleRepository roleRepository
    ) throws Exception {
		//Setup a default user if db is empty
		Role role1 = new Role("admin","admin description");
		Role role2 = new Role("comittee","comittee desc");
		Role role3 = new Role("participant","participant desc");
		Role role4 = new Role("client","client desc");

		roleRepository.save(role1);
        roleRepository.save(role2);
        roleRepository.save(role3);
        roleRepository.save(role4);


		if (userRepository.count()==0){
            userService.createUser(
                    new User(false,"admin","admin@mail.com","admin","ini photo",
                            Arrays.asList( role1, role2, role3, role4)));
        }

		User user2 = new User(false,"comittee1","com1@mail.com","comittee1","ini photo",
				Arrays.asList( role2, role3, role4 ));
		User user3 = new User(false,"comittee2","com2@mail.com","comittee2","ini photo",
				Arrays.asList( role2, role4 ));
		User user4 = new User(false,"participant1","part1@mail.com","participant1","ini photo",
				Arrays.asList( role3, role4 ));
		User user5 = new User(false,"participant2","part2@mail.com","participant2","ini photo",
				Arrays.asList( role4, role4 ));

		User user1 = userRepository.findByEmail("admin@mail.com");
        user2.setPassword(getPasswordEncoder().encode(user2.getPassword()));
        user3.setPassword(getPasswordEncoder().encode(user3.getPassword()));
        user4.setPassword(getPasswordEncoder().encode(user4.getPassword()));
        user5.setPassword(getPasswordEncoder().encode(user5.getPassword()));
		userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);


        Event event1 = new Event(
                "Donor Darah",
                "Donor darah event",
                new Date(118,3,22,10,10,10),
                new Date(118,3,28,10,10,10),
                Arrays.asList(
                        user1, user2
                ));

        Event event2 = new Event(
                "Entrepreneure Festival",
                "Entrepreneur festival event description",
                new Date(118,2,22,10,10,10),
                new Date(118,2,28,10,10,10),
                Arrays.asList(
                        user1, user2, user3
                ));

        Event event3 = new Event(
                "Seminar Programming Java",
                "Deskripsi seminar programing java",
                new Date(118,5,22,10,10,10),
                new Date(118,5,28,10,10,10),
                Arrays.asList(
                        user1, user2, user3
                ));

        Event event4 = new Event(
                "Seminar game pembuatan flappi bird",
                "deskripsi seminar pembuatan flappi bird",
                new Date(118,1,22,10,10,10),
                new Date(118,1,28,10,10,10),
                Arrays.asList(
                        user1, user2
                ));

        Event event5 = new Event(
                "Workshop ionix dan API",
                "deskripsi workshop ionix dan API",
                new Date(118,4,22,10,10,10),
                new Date(118,4,28,10,10,10),
                Arrays.asList(
                        user1, user2
                ));

        Event event6 = new Event(
                "Seminar Profesi ITU",
                "deskripsi seminar profesi ITU",
                new Date(118,10,22,10,10,10),
                new Date(118,10,28,10,10,10),
                Arrays.asList(
                        user1, user2
                ));


		eventRepository.save(event1);
		eventRepository.save(event2);
		eventRepository.save(event3);
		eventRepository.save(event4);
		eventRepository.save(event5);
		eventRepository.save(event6);


		builder.userDetailsService(userDetailsService(userRepository)).passwordEncoder(passwordEncoder);
	}


    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
	 * We return an istance of our CustomUserDetails.
	 * @param repository
	 * @return
	 */
	private UserDetailsService userDetailsService(final UserRepository repository) {
		return username -> new CustomUserDetails(repository.findByEmail(username));
	}
}
