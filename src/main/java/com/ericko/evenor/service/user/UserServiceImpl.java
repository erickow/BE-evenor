package com.ericko.evenor.service.user;

import com.ericko.evenor.configuration.FilesLocationConfig;
import com.ericko.evenor.entity.*;
import com.ericko.evenor.repository.*;
import com.ericko.evenor.service.storage.StorageService;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FilenameUtils;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private EventComitteeRepository eventComitteeRepository;

    @ApiParam
    private ReviewRepository reviewRepository;

    @Autowired
    @Qualifier("ImageStorageService")
    private StorageService imageStorageService;

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(UUID id) {
        return userRepository.findOne(id);
    }

    @Override
    public EventComittee getComittee(UUID userId, UUID eventId) {
        User comittee = userRepository.findOne(userId);
        Event event = eventRepository.findOne(eventId);
        return eventComitteeRepository.findByComitteeAndEvent(comittee, event);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user) {
        user.setPassword(getPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id) {
        User user = userRepository.findOne(id);
        List<Job> jobs = jobRepository.findAllByComittees_Comittee(user);
        EventComittee comittee = null;
        for(Job job : jobs){
            for(EventComittee eventComittee: job.getComittees()){
               if (user.equals(eventComittee.getComittee())){
                   comittee = eventComittee;
               }
            }
            job.getComittees().remove(comittee);
            jobRepository.save(job);
        }
        userRepository.delete(id);
    }

    @Override
    public List<User> searchByName(String name) {
        return userRepository.findAllByNameContains(name);
    }

    @Override
    public User uploadPhoto(UUID id, MultipartFile file) throws FileFormatException {
        String filename = FilenameUtils.removeExtension(file.getOriginalFilename());
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String imageName;

        User user = userRepository.findOne(id);
        if (Arrays.asList(FilesLocationConfig.Image.FILE_EXTENSION_ALLOWED).contains(extension)) {
            imageName = imageStorageService.storeOne(file, filename);
        }  else {
            throw new FileFormatException("Format tidak didukung");
        }
        user.setPhoto(imageName);
        return userRepository.save(user);
    }

    @Override
    public List<Volunteer> getVolunteer(UUID eventId, String type) {
        Event event = eventRepository.findOne(eventId);
        return volunteerRepository.findAllByEventAndType(event, type);
    }

    @Override
    public Volunteer createVolunteer(UUID userId, UUID eventId, String type) {
        User user = userRepository.findOne(userId);
        Event event = eventRepository.findOne(eventId);
        Volunteer volunteer = new Volunteer();
        volunteer.setUser(user);
        volunteer.setEvent(event);
        volunteer.setType(type);
        return volunteerRepository.save(volunteer);
    }

    @Override
    public void deleteVolunteer(UUID volunteerId) {
        volunteerRepository.delete(volunteerId);
    }

    @Override
    public Review getReview(UUID userId) {
        User user = userRepository.findOne(userId);
        List<Review> reviews = reviewRepository.findAllByUser(user);
        Review result = new Review();
        if(!reviews.isEmpty()){
            for (Review review : reviews){
                result.setLeadership(result.getLeadership() + review.getLeadership());
                result.setAuthority(result.getAuthority() + review.getAuthority());
                result.setDecision(result.getDecision() + review.getDecision());
                result.setFlexibility(result.getFlexibility() + review.getFlexibility());
            }
            result.setLeadership(result.getLeadership()/reviews.size());
            result.setAuthority(result.getAuthority()/reviews.size());
            result.setDecision(result.getDecision()/reviews.size());
            result.setFlexibility(result.getFlexibility()/reviews.size());
        }else{
            result.setLeadership(0);
            result.setAuthority(0);
            result.setDecision(0);
            result.setFlexibility(0);
        }
        return reviewRepository.save(result);
    }

    @Override
    public Review createReview(UUID userId,UUID reviewerId, UUID eventId, Integer leadership, Integer authority, Integer decision, Integer flexibility) {
        User user = userRepository.findOne(userId);
        User reviewer = userRepository.findOne(reviewerId);
        Event event = eventRepository.findOne(eventId);
        Review review = new Review();
        Review checkReview = reviewRepository.findByUserAndReviewerAndEvent(user, reviewer, event);
        if (checkReview == null){
            review.setUser(user);
            review.setReviewer(reviewer);
            review.setEvent(event);
            review.setLeadership(leadership);
            review.setAuthority(authority);
            review.setDecision(decision);
            review.setFlexibility(flexibility);
        }else{
            checkReview.setLeadership(leadership);
            checkReview.setAuthority(authority);
            checkReview.setDecision(decision);
            checkReview.setFlexibility(flexibility);
            review = checkReview;
        }
        return reviewRepository.save(review);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
