package com.ericko.evenor.service.event;

import com.ericko.evenor.entity.Event;
import com.ericko.evenor.entity.User;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Validated
public interface EventService {
    List<Event> getEvent();

    Event getEvent(UUID id);

    List<Event> getUpcommingEvent(UUID id);

    List<Event> getHistoryEvent(UUID id);

    Event createEvent(@Valid Event event);

    Event updateEvent(@Valid Event event);

    void deleteEvent(UUID id);
}
