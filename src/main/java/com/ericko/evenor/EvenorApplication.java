package com.ericko.evenor;

import com.ericko.evenor.entity.*;
import com.ericko.evenor.repository.*;
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
			EventRepository eventRepository,
            RoleRepository roleRepository,
			EventComitteeRepository eventComitteeRepository,
			EventParticipantRepository eventParticipantRepository,
			JobRepository jobRepository,
            TaskRepository taskRepository,
			QuestRepository questRepository
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

        User user1 = User.builder()
				.name("admin")
				.email("admin@mail.com")
				.password("admin")
				.active(false)
                .experience(100)
				.photo("media/image/default/default_avatar.jpg")
				.roles(Arrays.asList( role1, role2, role3, role4 ))
				.build();

		User user2 = User.builder()
				.name("comittee1")
				.email("com1@mail.com")
				.password("comittee1")
				.active(false)
				.photo("media/image/default/default_avatar.jpg")
                .experience(0)
				.roles(Arrays.asList( role2, role3, role4 ))
				.build();
		User user3 = User.builder()
				.name("comittee2")
				.email("com2@mail.com")
				.password("comittee2")
				.active(false)
				.photo("media/image/default/default_avatar.jpg")
                .experience(0)
				.roles(Arrays.asList( role2, role3, role4 ))
				.build();
		User user4 = User.builder()
				.name("participant")
				.email("par1@mail.com")
				.password("participant1")
				.active(false)
				.photo("media/image/default/default_avatar.jpg")
                .experience(0)
				.roles(Arrays.asList( role2, role3, role4 ))
				.build();
		User user5 = User.builder()
				.name("participan2")
				.email("par2@mail.com")
				.password("participant2")
				.active(false)
				.photo("media/image/default/default_avatar.jpg")
                .experience(0)
				.roles(Arrays.asList( role2, role3, role4 ))
				.build();

		user1.setPassword(getPasswordEncoder().encode(user1.getPassword()));
        user2.setPassword(getPasswordEncoder().encode(user2.getPassword()));
        user3.setPassword(getPasswordEncoder().encode(user3.getPassword()));
        user4.setPassword(getPasswordEncoder().encode(user4.getPassword()));
        user5.setPassword(getPasswordEncoder().encode(user5.getPassword()));
        userRepository.save(user1);
		userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);


        Event event1 = Event.builder()
				.name("1 Donor Darah")
				.description("Donor darah event")
				.startDate(new Date(118,3,22,10,10,10))
				.endDate( new Date(118,3,28,10,10,10))
				.photo("media/image/dummy/donordarah.jpg")
				.adminEvent(Arrays.asList(user1))
				.setComittee(true)
				.setParticipant(true)
				.build();

        Event event2 = Event.builder()
				.name("2 Entrepreneure Festival")
				.description("Entrepreneur festival event description")
				.startDate(new Date(118,2,22,10,10,10))
				.endDate(new Date(118,2,28,10,10,10))
				.photo("media/image/dummy/efest.jpg")
				.adminEvent(Arrays.asList(user1))
				.setComittee(true)
				.setParticipant(true)
				.build();


        Event event3 = Event.builder()
				.name("3 Programming Contest Carbon 3.7")
				.description("Deskripsi kontes programing ")
				.startDate(new Date(118,5,22,10,10,10))
				.endDate(new Date(118,5,28,10,10,10))
				.photo("media/image/dummy/programingcarbon.png")
				.adminEvent(Arrays.asList(user1))
				.setComittee(true)
				.setParticipant(true)
				.build();

        Event event4 = Event.builder()
				.name("4 Seminar game nasional raion mobile game")
				.description("deskripsi seminar game nasional ")
				.startDate(new Date(118,1,22,10,10,10))
				.endDate(new Date(118,1,28,10,10,10))
				.photo("media/image/dummy/seminargame.jpg")
				.adminEvent(Arrays.asList(user1))
				.setComittee(true)
				.setParticipant(true)
				.build();

        Event event5 = Event.builder()
				.name("5 Acara Ayodev Filkom UB")
				.description("deskripsi workshop development")
				.startDate(new Date(118,4,22,10,10,10))
				.endDate(new Date(118,4,28,10,10,10))
				.photo("media/image/dummy/ayodev.jpg")
				.adminEvent(Arrays.asList(user1))
				.setComittee(true)
				.setParticipant(true)
				.build();

        Event event6 = Event.builder()
				.name("6 Seminar Profesi ITU")
				.description("deskripsi seminar profesi ITU")
				.startDate(new Date(118,10,22,10,10,10))
				.endDate(new Date(118,10,28,10,10,10))
				.photo("media/image/dummy/seminarprofesi.jpg")
				.adminEvent(Arrays.asList(user1))
				.setComittee(true)
				.setParticipant(true)
				.build();


		eventRepository.save(event1);
		eventRepository.save(event2);
		eventRepository.save(event3);
		eventRepository.save(event4);
		eventRepository.save(event5);
		eventRepository.save(event6);

		EventComittee eventComittee1 = EventComittee.builder().comittee(user1).event(event1).score(0).build();
		EventComittee eventComittee2 = EventComittee.builder().comittee(user1).event(event2).score(0).build();
		EventComittee eventComittee3 = EventComittee.builder().comittee(user1).event(event3).score(0).build();
		EventComittee eventComittee4 = EventComittee.builder().comittee(user1).event(event4).score(0).build();
		EventComittee eventComittee5 = EventComittee.builder().comittee(user1).event(event5).score(0).build();
		EventComittee eventComittee6 = EventComittee.builder().comittee(user1).event(event6).score(0).build();
		EventComittee eventComittee7 = EventComittee.builder().comittee(user2).event(event1).score(0).build();
		EventComittee eventComittee8 = EventComittee.builder().comittee(user2).event(event2).score(0).build();
		EventComittee eventComittee9 = EventComittee.builder().comittee(user3).event(event3).score(0).build();
		EventComittee eventComittee10 = EventComittee.builder().comittee(user4).event(event4).score(0).build();
		EventComittee eventComittee11 = EventComittee.builder().comittee(user4).event(event5).score(0).build();
		EventComittee eventComittee12 = EventComittee.builder().comittee(user5).event(event6).score(0).build();

		eventComitteeRepository.save(eventComittee1);
		eventComitteeRepository.save(eventComittee2);
		eventComitteeRepository.save(eventComittee3);
		eventComitteeRepository.save(eventComittee4);
		eventComitteeRepository.save(eventComittee5);
		eventComitteeRepository.save(eventComittee6);
		eventComitteeRepository.save(eventComittee7);
		eventComitteeRepository.save(eventComittee8);
		eventComitteeRepository.save(eventComittee9);
		eventComitteeRepository.save(eventComittee10);
		eventComitteeRepository.save(eventComittee11);
		eventComitteeRepository.save(eventComittee12);

		EventParticipant eventParticipant1 = EventParticipant.builder().participant(user1).event(event3).build();
		EventParticipant eventParticipant2 = EventParticipant.builder().participant(user2).event(event3).build();
		EventParticipant eventParticipant3 = EventParticipant.builder().participant(user3).event(event3).build();
		EventParticipant eventParticipant4 = EventParticipant.builder().participant(user4).event(event3).build();
		EventParticipant eventParticipant5 = EventParticipant.builder().participant(user5).event(event3).build();
		EventParticipant eventParticipant6 = EventParticipant.builder().participant(user1).event(event2).build();
		EventParticipant eventParticipant7 = EventParticipant.builder().participant(user1).event(event4).build();
		EventParticipant eventParticipant8 = EventParticipant.builder().participant(user2).event(event4).build();
		EventParticipant eventParticipant9 = EventParticipant.builder().participant(user2).event(event5).build();
		EventParticipant eventParticipant10 = EventParticipant.builder().participant(user3).event(event1).build();
		EventParticipant eventParticipant11 = EventParticipant.builder().participant(user3).event(event2).build();
		EventParticipant eventParticipant12 = EventParticipant.builder().participant(user4).event(event1).build();
		EventParticipant eventParticipant13 = EventParticipant.builder().participant(user4).event(event6).build();
		EventParticipant eventParticipant14 = EventParticipant.builder().participant(user5).event(event6).build();
		EventParticipant eventParticipant15 = EventParticipant.builder().participant(user5).event(event1).build();

		eventParticipantRepository.save(eventParticipant1);
		eventParticipantRepository.save(eventParticipant2);
		eventParticipantRepository.save(eventParticipant3);
		eventParticipantRepository.save(eventParticipant4);
		eventParticipantRepository.save(eventParticipant5);
		eventParticipantRepository.save(eventParticipant6);
		eventParticipantRepository.save(eventParticipant7);
		eventParticipantRepository.save(eventParticipant8);
		eventParticipantRepository.save(eventParticipant9);
		eventParticipantRepository.save(eventParticipant10);
		eventParticipantRepository.save(eventParticipant11);
		eventParticipantRepository.save(eventParticipant12);
		eventParticipantRepository.save(eventParticipant13);
		eventParticipantRepository.save(eventParticipant14);
		eventParticipantRepository.save(eventParticipant15);

		Job job1 = Job.builder().name("memesan panggung").completion(false)
				.position(1).description("panggung harus sudah dipesan")
				.startDate(new Date(118,6,22,10,10,10))
				.endDate(new Date(118,6,28,10,10,10))
				.comittees(Arrays.asList(eventComittee3)).build();
		Job job2 = Job.builder().name("Rapat panitia").completion(false)
				.position(2).description("Rapat akan diadakan pada tanggal tertera diharapkan seluruh panitia dapat datang")
				.startDate(new Date(118,6,22,10,10,10))
				.endDate(new Date(118,6,28,10,10,10))
				.comittees(Arrays.asList(eventComittee3, eventComittee9)).build();
		Job job3 = Job.builder().name("Pesan Makanan").completion(false)
				.position(3).description("Makanan harus segera dipesan agar tidak mendadak")
				.startDate(new Date(118,6,22,10,10,10))
				.endDate(new Date(118,6,28,10,10,10))
				.comittees(Arrays.asList(eventComittee3, eventComittee9)).build();
		Job job4 = Job.builder().name("Sewa peralatan band").completion(false)
				.position(4).description("peralatan band yang harus disewa meliputi gitar, drum dan bass")
				.startDate(new Date(118,6,22,10,10,10))
				.endDate(new Date(118,6,28,10,10,10))
				.comittees(Arrays.asList(eventComittee3)).build();
		Job job5 = Job.builder().name("Pengerjaan dokumen").completion(false)
				.position(5).description("dokumen harus dikerjakan oleh sekretaris")
				.startDate(new Date(118,4,22,10,10,10))
				.endDate(new Date(118,4,28,10,10,10))
				.comittees(Arrays.asList(eventComittee3)).build();

		jobRepository.save(job1);
        jobRepository.save(job2);
        jobRepository.save(job3);
        jobRepository.save(job4);
        jobRepository.save(job5);

		Task task1 = Task.builder().name("List").position(1).event(event3).jobs(Arrays.asList(job1,job2,job3)).build();
        Task task2 = Task.builder().name("On going").position(2).event(event3).jobs(Arrays.asList(job4)).build();
        Task task3 = Task.builder().name("Done").position(3).event(event3).jobs(Arrays.asList(job5)).build();

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        Quest quest1 = Quest.builder().code("#ADD_EVENT")
							.name("Menambah Event")
							.description("membuat event baru untuk menambah point experience")
							.score(100)
							.build();
		Quest quest2 = Quest.builder().code("#ADD_VOTE")
				.name("Menambah Vote")
				.description("membuat vote baru untuk menambah point experience dan score panitia")
				.score(50)
				.build();
		Quest quest3 = Quest.builder().code("#ADD_VOTING")
				.name("Menambah Voting")
				.description("melakukan voting pada vote yang ada untuk menambah point experience dan score panitia")
				.score(50)
				.build();
		Quest quest4 = Quest.builder().code("#ADD_COMPLETION")
				.name("Menambah Rekap")
				.description("membuat rekap pada job yang diberikan untuk menambah point experience dan score panitia")
				.score(100)
				.build();
		Quest quest5 = Quest.builder().code("#ADD_COMPLETION_LATE")
				.name("Menambah Rekap")
				.description("membuat rekap pada job yang diberikan untuk menambah point experience dan score panitia")
				.score(25)
				.build();


		questRepository.save(quest1);
		questRepository.save(quest2);
		questRepository.save(quest3);
		questRepository.save(quest4);
		questRepository.save(quest5);

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
