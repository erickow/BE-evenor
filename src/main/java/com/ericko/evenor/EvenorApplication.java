package com.ericko.evenor;

import com.ericko.evenor.entity.CustomUserDetails;
import com.ericko.evenor.entity.Event;
import com.ericko.evenor.entity.Role;
import com.ericko.evenor.entity.User;
import com.ericko.evenor.repository.EventRepository;
import com.ericko.evenor.repository.UserRepository;
import com.ericko.evenor.service.event.EventService;
import com.ericko.evenor.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
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
	 * @param repository
	 * @throws Exception
	 */
	@Autowired
	public void authenticationManager(
            AuthenticationManagerBuilder builder,
            UserRepository repository,
            UserService userService,
            EventService eventService,
			EventRepository eventRepository
    ) throws Exception {
		//Setup a default user if db is empty
		Role role1 = new Role("admin","admin description");
		Role role2 = new Role("volunteer","volunteer desc");

		if (repository.count()==0){
            userService.createUser(
                    new User(false,"admin","admin@mail.com","admin","ini photo",
                            Arrays.asList( role1, role2)));
        }

        User user1 = repository.findByEmail("admin@mail.com");

        Event event1 = new Event(
                "Donor Darah",
                "Donor darah event",
                new Date(118,3,22,10,10,10),
                new Date(118,3,28,10,10,10),
                Arrays.asList(
                        user1
                ));

        Event event2 = new Event(
                "Entrepreneure Festival",
                "Entrepreneur festival event description",
                new Date(118,2,22,10,10,10),
                new Date(118,2,28,10,10,10),
                Arrays.asList(
                        user1
                ));

        Event event3 = new Event(
                "Seminar Programming Java",
                "Deskripsi seminar programing java",
                new Date(118,5,22,10,10,10),
                new Date(118,5,28,10,10,10),
                Arrays.asList(
                        user1
                ));

        Event event4 = new Event(
                "Seminar game pembuatan flappi bird",
                "deskripsi seminar pembuatan flappi bird",
                new Date(118,1,22,10,10,10),
                new Date(118,1,28,10,10,10),
                Arrays.asList(
                        user1
                ));

        Event event5 = new Event(
                "Workshop ionix dan API",
                "deskripsi workshop ionix dan API",
                new Date(118,4,22,10,10,10),
                new Date(118,4,28,10,10,10),
                Arrays.asList(
                        user1
                ));

        Event event6 = new Event(
                "Seminar Profesi ITU",
                "deskripsi seminar profesi ITU",
                new Date(118,10,22,10,10,10),
                new Date(118,10,28,10,10,10),
                Arrays.asList(
                        user1
                ));

		eventRepository.save(event1);
		eventRepository.save(event2);
		eventRepository.save(event3);
		eventRepository.save(event4);
		eventRepository.save(event5);
		eventRepository.save(event6);


		builder.userDetailsService(userDetailsService(repository)).passwordEncoder(passwordEncoder);
	}

	/**
	 * We return an istance of our CustomUserDetails.
	 * @param repository
	 * @return
	 */
	private UserDetailsService userDetailsService(final UserRepository repository) {
		return email -> new CustomUserDetails(repository.findByEmail(email));
	}
}
