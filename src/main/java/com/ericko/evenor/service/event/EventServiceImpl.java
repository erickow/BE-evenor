package com.ericko.evenor.service.event;

import com.ericko.evenor.entity.Event;
import com.ericko.evenor.entity.User;
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

    @Override
    public List<Event> getEvent() {
        return (List<Event>) eventRepository.findAll();
    }

    @Override
    public Event getEvent(UUID id) {
        return eventRepository.findOne(id);
    }

    @Override
    public List<Event> getUpcommingEvent(UUID id) {
        User user = userRepository.findOne(id);
        return eventRepository.findAllByComitteeContainsAndEndDateAfter(user, new Date());
    }

    @Override
    public List<Event> getHistoryEvent(UUID id) {
        User user = userRepository.findOne(id);
        return eventRepository.findAllByComitteeContainsAndEndDateBeforeOrderByEndDateDesc(user, new Date());
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
