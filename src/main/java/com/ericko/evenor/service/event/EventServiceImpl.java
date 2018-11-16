package com.ericko.evenor.service.event;

import com.ericko.evenor.entity.Event;
import com.ericko.evenor.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Event> getEvent() {
        return (List<Event>) eventRepository.findAll();
    }

    @Override
    public Event getEvent(UUID id) {
        return eventRepository.findOne(id);
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
