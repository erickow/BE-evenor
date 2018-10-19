package com.ericko.evenor.service.activity;

import com.ericko.evenor.entity.Activity;
import com.ericko.evenor.entity.Event;
import com.ericko.evenor.entity.EventComittee;
import com.ericko.evenor.entity.User;
import com.ericko.evenor.repository.ActivityRepository;
import com.ericko.evenor.repository.EventComitteeRepository;
import com.ericko.evenor.repository.EventRepository;
import com.ericko.evenor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventComitteeRepository eventComitteeRepository;

    @Override
    public List<Activity> getActivityByEvent(UUID id) {
        Event event = eventRepository.findOne(id);
        return activityRepository.findAllByEventOrderByDateDesc(event);
    }

    @Override
    public List<Activity> getActivityByUser(UUID id) {
        User user = userRepository.findOne(id);
        return activityRepository.findAllByUserOrderByDateDesc(user);
    }

    @Override
    public List<Activity> getActivityByAllEvent(UUID id) {
        User user = userRepository.findOne(id);
        List<EventComittee> eventComittees = eventComitteeRepository.findAllByComittee(user);
        List<Activity> activities = new ArrayList<>();
        for(EventComittee comittee : eventComittees){
            Event event = comittee.getEvent();
            activities.addAll(activityRepository.findAllByEventOrderByDateDesc(event));
        }
        activities.sort(Comparator.comparing(Activity::getDate));
        return activities;
    }

    @Override
    public Activity createActivity(String name, UUID idUser, UUID idEvent) {
        User user = userRepository.findOne(idUser);
        Event event = eventRepository.findOne(idEvent);
        Activity activity = Activity.builder().name(name).user(user).event(event).date(new Date()).build();
        return activityRepository.save(activity);
    }

    @Override
    public void deleteActivity(UUID id) {
        activityRepository.delete(id);
    }
}
