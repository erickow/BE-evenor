package com.ericko.evenor.service.event;

import com.ericko.evenor.entity.Event;
import com.ericko.evenor.entity.EventComittee;
import com.ericko.evenor.entity.EventParticipant;
import com.ericko.evenor.entity.User;
import com.ericko.evenor.repository.EventComitteeRepository;
import com.ericko.evenor.repository.EventParticipantRepository;
import com.ericko.evenor.repository.EventRepository;
import com.ericko.evenor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventComitteeRepository eventComitteeRepository;

    @Autowired
    private EventParticipantRepository eventParticipantRepository;

    @Override
    public List<Event> getEvent() {
        return (List<Event>) eventRepository.findAll();
    }

    @Override
    public Event getEvent(UUID id) {
        return eventRepository.findOne(id);
    }

    @Override
    public List<EventComittee> getUpcommingEvent(UUID id) {
        User user = userRepository.findOne(id);
        return eventComitteeRepository.findAllByComitteeAndEvent_EndDateAfter(user, new Date());
    }

    @Override
    public List<EventComittee> getHistoryEvent(UUID id) {
        User user = userRepository.findOne(id);
        return eventComitteeRepository.findAllByComitteeAndEvent_EndDateBefore(user, new Date());
    }

    @Override
    public List<EventComittee> getComittee(UUID id) {
        return eventComitteeRepository.findAllByEvent_Id(id);
    }

    @Override
    public List<EventParticipant> getUpcomminhParticipate(UUID id) {
        User user = userRepository.findOne(id);
        return eventParticipantRepository.findAllByParticipantAndEvent_EndDateAfter(user, new Date());
    }

    @Override
    public List<EventParticipant> getHistoryParticipate(UUID id) {
        User user = userRepository.findOne(id);
        return eventParticipantRepository.findAllByParticipantAndEvent_EndDateBefore(user, new Date());
    }

    @Override
    public List<EventParticipant> getParticipant(UUID id) {
        return eventParticipantRepository.findAllByEvent_Id(id);
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(UUID id) {
        eventRepository.delete(id);
    }
}
